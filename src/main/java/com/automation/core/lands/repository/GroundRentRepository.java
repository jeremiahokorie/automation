package com.automation.core.lands.repository;

import com.automation.core.lands.model.GroundRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroundRentRepository extends JpaRepository<GroundRent, Long> {

}
