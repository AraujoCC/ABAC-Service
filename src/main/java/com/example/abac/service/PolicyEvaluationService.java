package com.example.abac.service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PolicyEvaluationService {

    private final AttributeCollectorService pip;
    private final OpaClient opaClient;
    private final PolicyDecisionRepository decisionRepository;

    public AuthorizationResponse evaluate(AuthorizationRequest rawRequest) {
        String requestId = UUID.randomUUID().toString();

        // 1. PIP: enriquecer atributos faltantes
        AuthorizationRequest enriched = pip.enrich(rawRequest);

        // 2. PDP: delegar decisão ao OPA
        OpaDecisionResponse opaResponse = opaClient.evaluate(enriched);

        // 3. Persistir decisão para auditoria
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