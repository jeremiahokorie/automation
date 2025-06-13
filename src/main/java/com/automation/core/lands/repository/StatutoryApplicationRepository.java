package com.automation.core.lands.repository;

import com.automation.core.lands.model.StatutoryAllocationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutoryApplicationRepository extends JpaRepository<StatutoryAllocationApplication, Long> {

}
