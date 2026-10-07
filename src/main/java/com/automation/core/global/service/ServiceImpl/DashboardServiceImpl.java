package com.automation.core.global.service.ServiceImpl;

import com.automation.core.basepa.repository.EnvironmentRepository;
import com.automation.core.commerce.repository.BusinessRepository;
import com.automation.core.education.service.EducationService;
import com.automation.core.education.dto.response.EducationSummaryResponse;
import com.automation.core.global.dto.response.DashboardResponse;
import com.automation.core.global.service.UserService.DashboardService;
import com.automation.core.health.service.HealthService;
import com.automation.core.health.dto.response.HealthSummaryResponse;
import com.automation.core.lands.repository.CertificateOfOccupancyRepository;
import com.automation.core.lands.repository.GroundRentRepository;
import com.automation.core.lands.repository.StatutoryAllocationRepository;
import com.automation.core.mda.repository.mdaRepository;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
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
    private final EducationService educationService;
    private final HealthService healthService;

    @Override
    public DashboardResponse getDashboardSummary() {
        DashboardResponse response = new DashboardResponse();
        response.setTotalApprovedCofO((int) certificateRepository.countByStatus(Status.APPROVED));
        response.setTotalRejectedCofO((int) certificateRepository.countByStatus(Status.REJECTED));
        response.setTotalRegisteredBusiness(Math.toIntExact(businessRepository.count()));
        response.setTotalMdas(Math.toIntExact(mDaRepository.count()));
        response.setPendingCofO((int) certificateRepository.countByStatus(Status.PENDING));
        response.setPendingStatutory((int) statutoryAllocationRepository.countByStatus(Status.PENDING));
        response.setApprovedStatutory((int) statutoryAllocationRepository.countByStatus(Status.APPROVED));
        response.setPendingGroundRent((int) groundRentRepository.countByStatus(Status.PENDING));
        response.setApprovedGroundRent((int) groundRentRepository.countByStatus(Status.APPROVED));
        response.setPendingBusinessRegistration((int) businessRepository.countByStatus(Status.PENDING));

        EducationSummaryResponse eduSummary = educationService.getEducationSummary();
        response.setTotalRegisteredSchools((int) eduSummary.getTotalRegistered());
        response.setPendingSchools((int) eduSummary.getPending());
        response.setApprovedSchools((int) eduSummary.getApproved());
        response.setRejectedSchools((int) eduSummary.getRejected());

        HealthSummaryResponse healthSummary = healthService.getHealthSummary();
        response.setTotalRegisteredHealthFacilities((int) healthSummary.getTotalRegistered());
        response.setPendingHealthFacilities((int) healthSummary.getPending());
        response.setApprovedHealthFacilities((int) healthSummary.getApproved());
        response.setRejectedHealthFacilities((int) healthSummary.getRejected());

        return response;
    }
}
