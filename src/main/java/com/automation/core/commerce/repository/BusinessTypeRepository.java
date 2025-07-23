package com.automation.core.commerce.repository;


import com.automation.core.commerce.model.BusinessType;
import com.automation.core.global.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessType, Long> {
    BusinessType findByname(String name);

}
