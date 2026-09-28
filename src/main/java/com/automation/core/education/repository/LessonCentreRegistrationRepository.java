package com.automation.core.education.repository;

import com.automation.core.education.model.LessonCentre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonCentreRegistrationRepository extends JpaRepository<LessonCentre, Long> {
}
