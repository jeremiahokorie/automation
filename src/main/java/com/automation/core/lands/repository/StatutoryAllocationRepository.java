package com.automation.core.lands.repository;

import com.automation.core.lands.model.StatutoryAllocation;
import com.automation.util.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatutoryAllocationRepository extends JpaRepository<StatutoryAllocation, Long> {

    List<StatutoryAllocation> findByCreatedAtBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);

    long countByStatus(Status status);
}
