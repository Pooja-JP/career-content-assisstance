package com.poojajp.ai.career.service;

import com.poojajp.ai.career.dto.ResumeAnalysisResponseDto;
import com.poojajp.ai.career.dto.ResumeJobMatchResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiAiClientService {

    private final WebClient webClient;

    private final WebClient geminiWebClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateText(String prompt) {

        Map<String, Object> requestBody =
                Map.of(
                        "contents", new Object[]{
                                Map.of(
                                        "parts", new Object[]{
                                                Map.of("text", prompt)
                                        }
                                )
                        }
                );

        return webClient.post()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/models/gemini-pro:generateContent")
                                .queryParam("key", apiKey)
                                .build()
                )
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String analyzeResumePrompt(String resumeText, String jobDescription) {

        String prompt = """
        You are an ATS resume evaluator.

        Analyze the resume against the job description.

        Return the response strictly in this format:
        ATS_SCORE:
        MISSING_SKILLS:
        SUGGESTIONS:

        Resume:
        %s

        Job Description:
        %s
        """.formatted(resumeText, jobDescription);

        return generateText(prompt);
    }

    public ResumeAnalysisResponseDto parseResumeResponse(String aiResponse) {

        String atsScore = extract(aiResponse, "ATS_SCORE:");
        String missingSkills = extract(aiResponse, "MISSING_SKILLS:");
        String suggestions = extract(aiResponse, "SUGGESTIONS:");

        return new ResumeAnalysisResponseDto(atsScore, missingSkills, suggestions);
    }

    private String extract(String text, String key) {
        int start = text.indexOf(key);
        if (start == -1) return "Not available";
        int end = text.indexOf("\n", start + key.length());
        return end == -1
                ? text.substring(start + key.length()).trim()
                : text.substring(start + key.length(), end).trim();
    }

    public String generateResponse(String prompt) {

        log.info("Sending prompt to Gemini");

        return geminiWebClient.post()
                .bodyValue(buildRequest(prompt))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .doOnError(e -> log.error("Gemini API error", e))
                .block();
    }

    private Map<String, Object> buildRequest(String prompt) {
        return Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );
    }

    public String matchResumeWithJob(String resumeText, String jobDescription) {

        String prompt = """
    You are an ATS system.
    Analyze the resume against the job description.

    Return ONLY valid JSON in this format:
    {
      "matchPercentage": number,
      "missingSkills": ["skill1", "skill2"],
      "suggestions": "text"
    }

    Resume:
    %s

    Job Description:
    %s
    """.formatted(resumeText, jobDescription);

        return generateResponse(prompt);
    }


    // return generateResponse(prompt);

    public ResumeJobMatchResponseDto parseMatchResponse(String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(aiResponse, ResumeJobMatchResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response", e);
        }
    }
}
