package com.example.abac.infrastructure.opa;

import com.example.abac.dto.AuthorizationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpaClient {

    private final WebClient opaWebClient;

    public OpaDecisionResponse evaluate(AuthorizationRequest request) {
        log.warn("[OPA-STUB] retornando DENY por padrão até Fase 3");
        return new OpaDecisionResponse(false, "OPA not yet integrated", null, List.of());
    }
}