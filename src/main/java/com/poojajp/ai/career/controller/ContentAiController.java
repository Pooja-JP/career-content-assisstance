package com.poojajp.ai.career.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/content")
public class ContentAiController {

    @PostMapping("/generate")
    public String generate() {
        return "Content AI endpoint ready";
    }
}
