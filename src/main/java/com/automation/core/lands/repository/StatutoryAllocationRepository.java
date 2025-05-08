package com.automation.core.lands.repository;

import com.automation.core.lands.model.StatutoryAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutoryAllocationRepository extends JpaRepository<StatutoryAllocation, Long> {

}
