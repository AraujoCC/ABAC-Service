package com.example.abac.domain;

import com.example.abac.audit.AuditableEntity;
import com.example.abac.domain.enums.Action;
import com.example.abac.domain.enums.Effect;
import com.example.abac.domain.enums.ResourceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "policies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Policy extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Effect effect;

    private Integer priority;

    @Builder.Default
    private Boolean active = true;

    @ElementCollection
    @CollectionTable(name = "policy_target_actions",
            joinColumns = @JoinColumn(name = "policy_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Action> targetActions = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "policy_target_resource_types",
            joinColumns = @JoinColumn(name = "policy_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<ResourceType> targetResourceTypes = new HashSet<>();
}