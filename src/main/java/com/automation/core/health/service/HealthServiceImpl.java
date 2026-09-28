package com.automation.core.health.service;

import com.automation.core.health.dto.request.HealthFacilityRegistrationRequest;
import com.automation.core.health.dto.request.ResearchEthicalApprovalRequest;
import com.automation.core.health.dto.response.HealthRegistrationResponse;
import com.automation.core.health.model.HealthFacility;
import com.automation.core.health.model.ResearchEthicalApproval;
import com.automation.core.health.repository.HealthFacilityRegistrationRepository;
import com.automation.core.health.repository.ResearchEthicalApprovalRepository;
import com.automation.core.global.model.User;
import com.automation.core.tracking.enums.ApplicationStatus;
import com.automation.core.tracking.service.ApplicationTrackingService;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthServiceImpl implements HealthService {

    private final HealthFacilityRegistrationRepository facilityRepository;
    private final ResearchEthicalApprovalRepository researchRepository;
    private final ApplicationTrackingService trackingService;

    @Override
    @Transactional
    public HealthRegistrationResponse registerFacility(HealthFacilityRegistrationRequest request, User user) {
        HealthFacility facility = HealthFacility.builder()
                .facilityName(request.getFacilityName())
                .facilityType(request.getFacilityType())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .ownerName(request.getOwnerName())
                .createdBy(user)
                .status(Status.PENDING)
                .build();

        facility = facilityRepository.save(facility);

        // Register with global tracking system
        trackingService.registerApplication("HEALTH_FACILITY_REG", facility.getId(), user);

        return mapToResponse(facility);
    }

    @Override
    @Transactional
    public HealthRegistrationResponse registerResearch(ResearchEthicalApprovalRequest request, User user) {
        ResearchEthicalApproval research = ResearchEthicalApproval.builder()
                .projectTitle(request.getProjectTitle())
                .principalInvestigator(request.getPrincipalInvestigator())
                .institution(request.getInstitution())
                .createdBy(user)
                .status(Status.PENDING)
                .build();

        research = researchRepository.save(research);
        trackingService.registerApplication("RESEARCH_ETHICS", research.getId(), user);

        return mapToResponse(research);
    }

    @Override
    @Transactional
    public void updateFacilityStatus(Long id, Status status, String officer, String comment) {
        HealthFacility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facility not found"));

        facility.setStatus(status);
        facilityRepository.save(facility);

        ApplicationStatus trackingStatus = mapToTrackingStatus(status);
        trackingService.updateStatusByApplicationId(id, trackingStatus, status.name(), officer, comment);
    }

    @Override
    @Transactional
    public void updateResearchStatus(Long id, Status status, String officer, String comment) {
        ResearchEthicalApproval research = researchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Research app not found"));

        research.setStatus(status);
        researchRepository.save(research);

        ApplicationStatus trackingStatus = mapToTrackingStatus(status);
        trackingService.updateStatusByApplicationId(id, trackingStatus, status.name(), officer, comment);
    }

    @Override
    @Transactional
    public void approveFacility(Long id, String permitUrl) {
        HealthFacility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facility not found"));

        facility.setStatus(Status.APPROVED);
        facility.setPermitUrl(permitUrl);
        facility.setPermitGeneratedDate(LocalDateTime.now());
        facilityRepository.save(facility);

        trackingService.updateStatusByApplicationId(id, ApplicationStatus.APPROVED, "APPROVED", "System", "Licence issued successfully");
    }

    private HealthRegistrationResponse mapToResponse(HealthFacility facility) {
        HealthRegistrationResponse response = new HealthRegistrationResponse();
        response.setId(facility.getId());
        response.setFacilityName(facility.getFacilityName());
        response.setStatus(facility.getStatus().name());
        response.setPermitUrl(facility.getPermitUrl());
        response.setCreatedAt(facility.getCreatedAt());
        return response;
    }

    private HealthRegistrationResponse mapToResponse(ResearchEthicalApproval research) {
        HealthRegistrationResponse response = new HealthRegistrationResponse();
        response.setId(research.getId());
        response.setFacilityName(research.getProjectTitle());
        response.setStatus(research.getStatus().name());
        response.setPermitUrl(research.getPermitUrl());
        response.setCreatedAt(research.getCreatedAt());
        return response;
    }

    private ApplicationStatus mapToTrackingStatus(Status status) {
        return switch (status) {
            case PENDING -> ApplicationStatus.PENDING;
            case UNDER_REVIEW -> ApplicationStatus.IN_REVIEW;
            case REVIEWED -> ApplicationStatus.ACTION_REQUIRED; // For inspection
            case VERIFIED -> ApplicationStatus.IN_REVIEW;
            case APPROVED -> ApplicationStatus.APPROVED;
            case REJECTED -> ApplicationStatus.REJECTED;
            default -> ApplicationStatus.PENDING;
        };
    }
}
