package com.automation.core.education.repository;

import com.automation.core.education.model.SchoolRegistration;
import com.automation.util.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRegistrationRepository extends JpaRepository<SchoolRegistration, Long> {
    long countByStatus(Status status);
    Page<SchoolRegistration> findAllByStatus(Status status, Pageable pageable);
}
