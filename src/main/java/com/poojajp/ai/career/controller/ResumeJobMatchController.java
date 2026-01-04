package com.poojajp.ai.career.controller;

import com.poojajp.ai.career.dto.ResumeJobMatchResponseDto;
import com.poojajp.ai.career.entity.ResumeMatchEntity;
import com.poojajp.ai.career.repository.ResumeMatchRepository;
import com.poojajp.ai.career.service.GeminiAiClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ai/resume-job")
@RequiredArgsConstructor
public class ResumeJobMatchController {

    private final GeminiAiClientService geminiService;
    private final ResumeMatchRepository repository;

    @PostMapping("/match")
    public ResumeJobMatchResponseDto matchResumeAndJob(
            @RequestParam String resumeText,
            @RequestParam String jobDescription) {

        String aiResponse = geminiService.matchResumeWithJob(resumeText, jobDescription);

        ResumeJobMatchResponseDto dto =
                geminiService.parseMatchResponse(aiResponse);

        ResumeMatchEntity entity = ResumeMatchEntity.builder()
                .resumeText(resumeText)
                .jobDescription(jobDescription)
                .aiResponse(aiResponse)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(entity);

        return dto;
    }

}
