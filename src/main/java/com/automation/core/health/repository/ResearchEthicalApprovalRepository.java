package com.automation.core.health.repository;

import com.automation.core.health.model.ResearchEthicalApproval;
import com.automation.util.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchEthicalApprovalRepository extends JpaRepository<ResearchEthicalApproval, Long> {
    long countByStatus(Status status);
    Page<ResearchEthicalApproval> findAllByStatus(Status status, Pageable pageable);
}
