package com.poojajp.ai.career.repository;

import com.poojajp.ai.career.entity.ResumeMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeMatchRepository extends JpaRepository<ResumeMatchEntity, Long> {
}
