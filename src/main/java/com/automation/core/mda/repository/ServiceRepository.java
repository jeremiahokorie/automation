package com.automation.core.mda.repository;

import com.automation.core.mda.model.ServicesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServicesModel,Long> {
    List<ServicesModel> findByMdaId(Long mdaId);
    void deleteByMdaId(Long mdaId);
}
