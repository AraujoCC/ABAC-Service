package com.example.abac.repository;

import com.example.abac.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResourceRepository extends JpaRepository<Resource, UUID> {
    Optional<Resource> findByResourceId(String resourceId);
}