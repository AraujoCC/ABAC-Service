package com.example.abac.pip;

import com.example.abac.dto.AuthorizationRequest;
import com.example.abac.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttributeCollectorService {

    private final SubjectRepository subjectRepository;

    public AuthorizationRequest enrich(AuthorizationRequest request) {
        return subjectRepository.findByUserId(request.subjectId())
                .map(subject -> AuthorizationRequest.builder()
                        .subjectId(request.subjectId())
                        .department(subject.getDepartment())
                        .role(subject.getRole())
                        .clearanceLevel(subject.getClearanceLevel())
                        .contractType(subject.getContractType() != null
                                ? subject.getContractType().name() : null)
                        .location(subject.getLocation())
                        .resourceId(request.resourceId())
                        .resourceType(request.resourceType())
                        .ownerDepartment(request.ownerDepartment())
                        .classification(request.classification())
                        .action(request.action())
                        .timestamp(request.timestamp())
                        .ipAddress(request.ipAddress())
                        .deviceTrusted(request.deviceTrusted())
                        .build())
                .orElse(request);
    }
}