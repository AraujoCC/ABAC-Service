package com.example.abac.service;

import com.example.abac.domain.PolicyDecision;
import com.example.abac.domain.enums.Effect;
import com.example.abac.dto.AuthorizationRequest;
import com.example.abac.dto.AuthorizationResponse;
import com.example.abac.infrastructure.opa.OpaClient;
import com.example.abac.infrastructure.opa.OpaDecisionResponse;
import com.example.abac.pip.AttributeCollectorService;
import com.example.abac.repository.PolicyDecisionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PolicyEvaluationService {

    private final AttributeCollectorService pip;
    private final OpaClient opaClient;
    private final PolicyDecisionRepository decisionRepository;

    public AuthorizationResponse evaluate(AuthorizationRequest rawRequest) {
        String requestId = UUID.randomUUID().toString();

        AuthorizationRequest enriched = pip.enrich(rawRequest);

        OpaDecisionResponse opaResponse = opaClient.evaluate(enriched);

        Effect decision = opaResponse.allow() ? Effect.PERMIT : Effect.DENY;
        persistDecision(enriched, decision, opaResponse.reason(), requestId);

        log.info("[ABAC] requestId={} subject={} resource={} action={} decision={}",
                requestId, enriched.subjectId(), enriched.resourceId(),
                enriched.action(), decision);

        return AuthorizationResponse.builder()
                .permit(opaResponse.allow())
                .reason(opaResponse.reason())
                .policyApplied(opaResponse.policyApplied())
                .obligations(opaResponse.obligations())
                .requestId(requestId)
                .build();
    }

    private void persistDecision(AuthorizationRequest req,
                                 Effect decision, String reason, String requestId) {
        decisionRepository.save(PolicyDecision.builder()
                .subjectId(req.subjectId())
                .resourceId(req.resourceId())
                .action(req.action())
                .decision(decision)
                .reason(reason)
                .requestId(requestId)
                .evaluatedAt(Instant.now())
                .ipAddress(req.ipAddress())
                .build());
    }
}