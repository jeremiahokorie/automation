package com.automation.core.health.repository;

import com.automation.core.health.model.HealthFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthFacilityRegistrationRepository extends JpaRepository<HealthFacility, Long> {
}
