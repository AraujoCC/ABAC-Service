package com.example.abac.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AuthorizationResponse(
        boolean permit,
        String reason,
        String policyApplied,
        List<String> obligations,
        String requestId
) {
}