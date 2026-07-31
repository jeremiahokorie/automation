package com.automation.core.lga.repository;

import com.automation.core.lga.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, String> {
    List<Ward> findByLocalGovernmentIdOrderByNameAsc(String lgaId);
    boolean existsByNameIgnoreCaseAndLocalGovernmentId(String name, String lgaId);
}
