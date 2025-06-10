package com.automation.core.lands.repository;

import com.automation.core.lands.model.CustomaryAllocationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomaryAllocationRepository extends JpaRepository<CustomaryAllocationApplication, Long> {
}
