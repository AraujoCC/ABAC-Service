package com.example.abac.infrastructure.opa;

import java.util.List;

public record OpaDecisionResponse(
        boolean allow,
        String reason,
        String policyApplied,
        List<String> obligations
) {
}