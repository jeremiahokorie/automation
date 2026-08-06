package com.automation.core.abiaid.repository;

import com.automation.core.abiaid.model.AbiaStateIdentification;
import com.automation.core.global.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbiaStateIdentificationRepository extends JpaRepository<AbiaStateIdentification, Long> {
    Optional<AbiaStateIdentification> findByAbiaIdNumber(String abiaIdNumber);
    List<AbiaStateIdentification> findByCreatedByOrderByIdDesc(User user);
}
