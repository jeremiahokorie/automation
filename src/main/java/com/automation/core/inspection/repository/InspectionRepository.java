package com.automation.core.inspection.repository;

import com.automation.core.basepa.model.EnvironmentApplication;
import com.automation.core.commerce.model.BusinessRegistration;
import com.automation.core.inspection.model.Inspection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {
    List<Inspection> findAllByOrderByCreatedAtDesc();
    Page<Inspection> findAll(Pageable pageable);

}
