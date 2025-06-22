package com.automation.core.lands.repository;

import com.automation.core.lands.model.CustomaryAllocationApplication;
import com.automation.util.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomaryAllocationRepository extends JpaRepository<CustomaryAllocationApplication, Long> {
    long countByStatus(Status status);

}
