package com.automation.core.global.repository;

import com.automation.core.global.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByname(String name);

    Optional<Roles> findByValue(String value);
}
