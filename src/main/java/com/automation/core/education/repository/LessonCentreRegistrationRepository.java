package com.automation.core.education.repository;

import com.automation.core.education.model.LessonCentre;
import com.automation.util.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonCentreRegistrationRepository extends JpaRepository<LessonCentre, Long> {
    long countByStatus(Status status);
    Page<LessonCentre> findAllByStatus(Status status, Pageable pageable);
}
