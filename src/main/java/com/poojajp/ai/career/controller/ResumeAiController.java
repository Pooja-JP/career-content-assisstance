package com.poojajp.ai.career.controller;

import com.poojajp.ai.career.dto.GeminiRequest;
import com.poojajp.ai.career.dto.ResumeAnalysisRequestDto;
import com.poojajp.ai.career.dto.ResumeAnalysisResponseDto;
import com.poojajp.ai.career.service.GeminiAiClientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/resume")
@RequiredArgsConstructor
public class ResumeAiController {

    private final GeminiAiClientService geminiAiClientService;

    private final GeminiAiClientService geminiService;

    @Operation(
            summary = "Analyze a Java developer resume",
            description = "Accepts a resume text or prompt and returns 3 improvement tips for better ATS scoring"
    )
    @PostMapping("/analyze")
    public ResumeAnalysisResponseDto analyzeResume(
            @Valid @RequestBody ResumeAnalysisRequestDto request) {

        String aiResponse = geminiAiClientService.analyzeResumePrompt(
                request.getResumeText(),
                request.getJobDescription()
        );

        return geminiAiClientService.parseResumeResponse(aiResponse);
    }

    @Operation(
            summary = "Generate AI response using Google Gemini",
            description = "Accepts a prompt and returns AI-generated content"
    )
    @PostMapping("/generate")
    public ResponseEntity<String> generate(@Valid @RequestBody GeminiRequest request) {
        String response = geminiService.generateResponse(request.getPrompt());
        return ResponseEntity.ok(response);
    }

}
