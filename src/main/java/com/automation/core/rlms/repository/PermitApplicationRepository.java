package com.automation.core.rlms.repository;

import com.automation.core.rlms.model.PermitApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermitApplicationRepository extends JpaRepository<PermitApplication, Long> {


}
