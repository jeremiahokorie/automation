package com.automation.core.mda.repository;

import com.automation.core.mda.model.mdaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface mdaRepository extends JpaRepository<mdaModel, Integer> {
    boolean existsByCode(String code);
    void deleteByCode(String code);
    Optional<mdaModel> findBycode(String code);
}
