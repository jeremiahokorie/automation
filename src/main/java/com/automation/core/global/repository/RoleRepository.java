package com.automation.core.global.repository;

import com.automation.core.global.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByname(String name);

    Optional<Role> findByValue(String value);
}
