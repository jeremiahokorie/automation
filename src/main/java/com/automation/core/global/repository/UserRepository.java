package com.automation.core.global.repository;

import com.automation.core.global.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByemail(String email);

    List<User> findAllByOrderByCreatedAtDesc();
}
