package com.automation.core.education.repository;

import com.automation.core.education.model.SchoolRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRegistrationRepository extends JpaRepository<SchoolRegistration, Long> {
}
