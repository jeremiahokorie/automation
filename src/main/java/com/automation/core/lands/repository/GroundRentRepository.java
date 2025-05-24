package com.automation.core.lands.repository;

import com.automation.core.lands.model.GroundRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GroundRentRepository extends JpaRepository<GroundRent, Long> {

    List<GroundRent> findByCreatedAtBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
