package com.automation.core.lands.repository;

import com.automation.core.lands.model.CertificateOfOccupancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateOfOccupancyRepository extends JpaRepository<CertificateOfOccupancy, Long> {
}
