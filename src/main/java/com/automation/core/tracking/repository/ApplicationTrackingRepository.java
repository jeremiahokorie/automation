package com.automation.core.tracking.repository;

import com.automation.core.tracking.model.ApplicationTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationTrackingRepository extends JpaRepository<ApplicationTracking, Long>, JpaSpecificationExecutor<ApplicationTracking> {
    Optional<ApplicationTracking> findByTrackingReference(String trackingReference);
    Optional<ApplicationTracking> findByApplicationId(Long applicationId);
}
