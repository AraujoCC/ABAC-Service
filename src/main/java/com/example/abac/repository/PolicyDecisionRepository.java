package com.example.abac.repository;

import com.example.abac.domain.PolicyDecision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface PolicyDecisionRepository extends JpaRepository<PolicyDecision, UUID> {
    List<PolicyDecision> findBySubjectIdAndEvaluatedAtAfter(String subjectId, Instant since);
}