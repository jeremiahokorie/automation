package com.automation.core.commerce.repository;

import com.automation.core.basepa.model.EnvironmentApplication;
import com.automation.core.commerce.model.BusinessRegistration;
import com.automation.util.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessRegistration, Long> {
    BusinessRegistration findBybusinessNumber(String businessNumber);

    long countByStatus(Status status);
    List<BusinessRegistration> findAllByOrderByCreatedAtDesc();
}
