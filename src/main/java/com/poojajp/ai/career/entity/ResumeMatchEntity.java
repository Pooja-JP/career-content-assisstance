package com.poojajp.ai.career.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeMatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String resumeText;

    @Column(length = 3000)
    private String jobDescription;

    @Column(length = 5000)
    private String aiResponse;

    private LocalDateTime createdAt;
}
