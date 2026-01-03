package com.poojajp.ai.career.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiAiClientService {

    private final WebClient webClient;

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
}
