package com.automation.core.lands.repository;

import com.automation.core.lands.model.LandApplication;
import com.automation.util.enums.LandApplicationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LandApplicationRepository extends JpaRepository<LandApplication, Long> {

    List<LandApplication> findByApplicationTypeAndApplicationDateBetween(LandApplicationType type, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
