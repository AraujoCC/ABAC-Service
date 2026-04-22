package com.example.abac.dto;

import lombok.Builder;

@Builder
public record SubjectAttributes(
        String userId,
        String department,
        String role,
        Integer clearanceLevel,
        String contractType,
        String location,
        Boolean active
) {
}