package com.example.abac.domain;

import com.example.abac.domain.enums.Action;
import com.example.abac.domain.enums.Effect;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "policy_decisions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String subjectId;

    @Column(nullable = false)
    private String resourceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Action action;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Effect decision;

    private String policyApplied;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(nullable = false)
    private Instant evaluatedAt;

    private String ipAddress;
    private String requestId;
}