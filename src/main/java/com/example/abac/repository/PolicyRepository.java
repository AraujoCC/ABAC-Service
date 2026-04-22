package com.example.abac.repository;

import com.example.abac.domain.Policy;
import com.example.abac.domain.enums.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PolicyRepository extends JpaRepository<Policy, UUID> {
    List<Policy> findByActiveTrueOrderByPriorityAsc();

    List<Policy> findByTargetActionsContainingAndActiveTrueOrderByPriorityAsc(Action action);
}