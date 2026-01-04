package com.poojajp.ai.career.dto;

public class ResumeAnalysisResponseDto {

    private String atsScore;
    private String missingSkills;
    private String suggestions;

    public ResumeAnalysisResponseDto(String atsScore, String missingSkills, String suggestions) {
        this.atsScore = atsScore;
        this.missingSkills = missingSkills;
        this.suggestions = suggestions;
    }

    public String getAtsScore() {
        return atsScore;
    }

    public String getMissingSkills() {
        return missingSkills;
    }

    public String getSuggestions() {
        return suggestions;
    }
}
