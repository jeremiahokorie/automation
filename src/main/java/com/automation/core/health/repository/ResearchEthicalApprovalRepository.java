package com.automation.core.health.repository;

import com.automation.core.health.model.ResearchEthicalApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchEthicalApprovalRepository extends JpaRepository<ResearchEthicalApproval, Long> {
}
