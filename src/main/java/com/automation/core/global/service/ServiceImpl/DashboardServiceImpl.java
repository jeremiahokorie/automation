package com.automation.core.global.service.ServiceImpl;

import com.automation.core.basepa.repository.EnvironmentRepository;
import com.automation.core.commerce.repository.BusinessRepository;
import com.automation.core.global.service.UserService.DashboardService;
import com.automation.core.lands.repository.CertificateOfOccupancyRepository;
import com.automation.core.lands.repository.GroundRentRepository;
import com.automation.core.lands.repository.StatutoryAllocationRepository;
import com.automation.core.mda.repository.mdaRepository;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService {
    private final EnvironmentRepository environmentRepository;
    private final BusinessRepository businessRepository;
    private final mdaRepository mDaRepository;
    private final GroundRentRepository groundRentRepository;
    private final StatutoryAllocationRepository statutoryAllocationRepository;
    private final CertificateOfOccupancyRepository certificateRepository;


    @Override
    public Integer getTotalApprovedCofO() {
        return (int) certificateRepository.countByStatus(Status.APPROVED);
    }

    @Override
    public Integer getTotalRejectedCofO() {
        return (int) certificateRepository.countByStatus(Status.REJECTED);
    }

    @Override
    public Integer getTotalRegisteredBusiness() {
        return Math.toIntExact(businessRepository.count());
    }

    @Override
    public Integer getTotalMdas() {
        return Math.toIntExact(mDaRepository.count());
    }

    @Override
    public Integer getPendingCofO() {
        return (int) certificateRepository.countByStatus(Status.PENDING);
    }

    @Override
    public Integer getPendingStatutory() {
        return (int) statutoryAllocationRepository.countByStatus(Status.PENDING);
    }

    @Override
    public Integer getApprovedStatutory() {
        return (int) statutoryAllocationRepository.countByStatus(Status.APPROVED);
    }

    @Override
    public Integer getPendingGroundRent() {
        return (int) groundRentRepository.countByStatus(Status.PENDING);
    }

    @Override
    public Integer getApprovedGroundRent() {
        return (int) groundRentRepository.countByStatus(Status.APPROVED);
    }

    @Override
    public Integer getPendingBusinessRegisteration() {
        return (int) businessRepository.countByStatus(Status.PENDING);
    }
}
