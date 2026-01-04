package com.poojajp.ai.career.controller;

import com.poojajp.ai.career.dto.ResumeAnalysisResponseDto;
import com.poojajp.ai.career.service.GeminiAiClientService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/ai/resume-file")
@RequiredArgsConstructor
public class ResumeFileController {

    private final GeminiAiClientService geminiService;

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResumeAnalysisResponseDto analyzeResumeFile(@RequestParam("file") MultipartFile file) throws IOException {

        // Extract text from PDF
        PDDocument document = PDDocument.load(file.getInputStream());
        String resumeText = new PDFTextStripper().getText(document);
        document.close();

        // Prompt Gemini
        String aiResponse = geminiService.analyzeResumePrompt(resumeText, "Generic Job Description");

        // Parse structured output
        return geminiService.parseResumeResponse(aiResponse);
    }
}
