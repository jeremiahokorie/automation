package com.automation.core.basepa.repository;

import com.automation.core.basepa.model.EnvironmentApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends JpaRepository<EnvironmentApplication, Long> {
    EnvironmentApplication findByemail(String email);
}
