package com.poojajp.ai.career.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/resume")
public class ResumeAiController {

    @PostMapping("/analyze")
    public String analyze() {
        return "Resume AI endpoint ready";
    }
}
