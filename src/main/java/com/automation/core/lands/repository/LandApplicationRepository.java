package com.automation.core.lands.repository;

import com.automation.core.lands.model.LandApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandApplicationRepository extends JpaRepository<LandApplication, Long> {

}
