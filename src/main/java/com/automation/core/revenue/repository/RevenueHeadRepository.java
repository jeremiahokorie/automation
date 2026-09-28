package com.automation.core.revenue.repository;

import com.automation.core.revenue.model.RevenueHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RevenueHeadRepository extends JpaRepository<RevenueHead, Long> {
    Optional<RevenueHead> findByRevenueHeadCode(String revenueHeadCode);
}
