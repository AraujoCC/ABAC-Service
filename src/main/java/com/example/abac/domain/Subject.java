package com.example.abac.domain;

import com.example.abac.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String role;

    private Integer clearanceLevel;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    private String location;

    @Builder.Default
    private Boolean active = true;

    public enum ContractType {PERMANENT, CONTRACTOR, NOTICE_PERIOD}
}