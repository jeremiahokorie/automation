package com.automation.core.basepa.repository;

import com.automation.core.basepa.model.EnvironmentApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnvironmentRepository extends JpaRepository<EnvironmentApplication, Long> {
    EnvironmentApplication findByemail(String email);

    Optional<EnvironmentApplication> findByoperationalLicenseNumber(String operationalLicenseNumber);
}
