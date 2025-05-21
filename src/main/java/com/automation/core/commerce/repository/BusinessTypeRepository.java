package com.automation.core.commerce.repository;


import com.automation.core.commerce.model.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessType, Long> {
    BusinessType findByname(String name);
}
