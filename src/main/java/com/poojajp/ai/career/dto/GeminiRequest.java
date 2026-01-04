package com.poojajp.ai.career.dto;

import jakarta.validation.constraints.NotBlank;

public class GeminiRequest {

    @NotBlank(message = "Prompt cannot be empty")
    private String prompt;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
