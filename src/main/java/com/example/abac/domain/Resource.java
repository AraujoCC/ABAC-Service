package com.example.abac.domain;

import com.example.abac.audit.AuditableEntity;
import com.example.abac.domain.enums.Classification;
import com.example.abac.domain.enums.ResourceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "resources")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String resourceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResourceType resourceType;

    private String ownerId;
    private String ownerDepartment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Classification classification;

    @Builder.Default
    private Boolean archived = false;
}