package com.poojajp.ai.career.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeJobMatchResponseDto {

    private int matchPercentage;
    private List<String> missingSkills;
    private String suggestions;
}
