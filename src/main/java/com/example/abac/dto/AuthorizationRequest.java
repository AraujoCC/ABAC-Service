package com.example.abac.dto;

import com.example.abac.domain.enums.Action;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Instant;

@Builder
public record AuthorizationRequest(

        @NotBlank String subjectId,
        String department,
        String role,
        Integer clearanceLevel,
        String contractType,

        @NotBlank String resourceId,
        String resourceType,
        String ownerDepartment,
        String classification,

        @NotNull Action action,

        Instant timestamp,
        String ipAddress,
        Boolean deviceTrusted,
        String location
) {
}