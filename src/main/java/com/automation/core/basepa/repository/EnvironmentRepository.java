package com.automation.core.basepa.repository;

import com.automation.core.basepa.model.EnvironmentApplication;
import com.automation.core.commerce.model.BusinessRegistration;
import com.automation.core.global.model.User;
import com.automation.util.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvironmentRepository extends JpaRepository<EnvironmentApplication, Long> {
    EnvironmentApplication findByemail(String email);

    Optional<EnvironmentApplication> findByoperationalLicenseNumber(String operationalLicenseNumber);

    long countByStatus(Status status);

    List<EnvironmentApplication> findAllByOrderByCreatedAtDesc();

    Page<EnvironmentApplication> findAll(Pageable pageable);
}
