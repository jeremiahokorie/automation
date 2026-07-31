package com.automation.core.lga.repository;

import com.automation.core.lga.model.LocalGovernment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalGovernmentRepository extends JpaRepository<LocalGovernment, String> {
    Optional<LocalGovernment> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}