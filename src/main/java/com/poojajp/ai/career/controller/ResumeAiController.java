package com.poojajp.ai.career.controller;

import com.poojajp.ai.career.service.GeminiAiClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/resume")
@RequiredArgsConstructor
public class ResumeAiController {

    private final GeminiAiClientService geminiAiClientService;

    @PostMapping("/analyze")
    public String analyzeResume() {

        String prompt = """
                You are an ATS resume evaluator.
                Analyze a Java developer resume and give 3 improvement tips.
                """;

        return geminiAiClientService.generateText(prompt);
    }
}
