package com.automation.core.tracking.repository;

import com.automation.core.tracking.model.ApplicationTrackingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationTrackingHistoryRepository extends JpaRepository<ApplicationTrackingHistory, Long> {
    List<ApplicationTrackingHistory> findByTrackingIdOrderByCreatedAtAsc(Long trackingId);
}
