package com.automation.core.health.service;

import com.automation.core.education.model.SchoolRegistration;
import com.automation.core.health.dto.request.HealthFacilityRegistrationRequest;
import com.automation.core.health.dto.request.ResearchEthicalApprovalRequest;
import com.automation.core.health.dto.response.HealthRegistrationResponse;
import com.automation.core.health.dto.response.HealthSummaryResponse;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.health.model.HealthFacility;
import com.automation.core.health.model.ResearchEthicalApproval;
import com.automation.core.health.repository.HealthFacilityRegistrationRepository;
import com.automation.core.health.repository.ResearchEthicalApprovalRepository;
import com.automation.core.inspection.dto.request.InspectionRequest;
import com.automation.core.inspection.model.Inspection;
import com.automation.core.inspection.repository.InspectionRepository;
import com.automation.core.inspection.service.InspectionService.InspectionService;
import com.automation.core.tracking.enums.ApplicationStatus;
import com.automation.core.tracking.service.ApplicationTrackingService;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthServiceImpl implements HealthService {

    private final HealthFacilityRegistrationRepository facilityRepository;
    private final ResearchEthicalApprovalRepository researchRepository;
    private final ApplicationTrackingService trackingService;
    private final UserRepository userRepository;
    private final InspectionRepository inspectionRepository;
    private final InspectionService inspectionService;

    @Override
    @Transactional
    public HealthRegistrationResponse registerFacility(HealthFacilityRegistrationRequest request) {
        try {
            HealthFacility facility = HealthFacility.builder()
                    .facilityName(request.getFacilityName())
                    .facilityType(request.getFacilityType())
                    .address(request.getAddress())
                    .phone(request.getPhone())
                    .email(request.getEmail())
                    .ownerName(request.getOwnerName())
                    .createdBy(getCurrentUser())
                    .status(Status.PENDING)
                    .build();

            facility = facilityRepository.save(facility);
            trackingService.registerApplication("HEALTH_FACILITY_REG", facility.getId(), getCurrentUser());
            createInspectionRequest(facility.getFacilityName(), "SCHOOL", facility);
            return mapToResponse(facility);

        } catch (Exception e) {
            log.error("Error occurred while registering health facility: {}", e.getMessage(), e);
            throw new RuntimeException("Submission failed due to a system error. Please try again later.");
        }
    }

    @Override
    @Transactional
    public HealthRegistrationResponse registerResearch(ResearchEthicalApprovalRequest request) {
//        log.info("Registering research ethical approval: {} by user: {}", request.getProjectTitle(), user != null ? user.getId() : "unknown");
//
//        if (user == null) {
//            log.error("Registration failed: Authenticated user is null");
//            throw new RuntimeException("User authentication is required for registration");
//        }

        try {
            ResearchEthicalApproval research = ResearchEthicalApproval.builder()
                    .projectTitle(request.getProjectTitle())
                    .principalInvestigator(request.getPrincipalInvestigator())
                    .institution(request.getInstitution())
                    .createdBy(getCurrentUser())
                    .status(Status.PENDING)
                    .build();

            research = researchRepository.save(research);
            trackingService.registerApplication("RESEARCH_ETHICS", research.getId(), getCurrentUser());


            return mapToResponse(research);
        } catch (Exception e) {
            log.error("Error occurred while registering research approval: {}", e.getMessage(), e);
            throw new RuntimeException("Submission failed due to a system error. Please try again later.");
        }
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

    @Override
    public Page<HealthRegistrationResponse> getFacilities(int page, int size, String sortBy, String sortDir, Status status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (status != null) {
            return facilityRepository.findAllByStatus(status, pageable).map(this::mapToResponse);
        }
        return facilityRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Page<HealthRegistrationResponse> getResearchApplications(int page, int size, String sortBy, String sortDir, Status status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (status != null) {
            return researchRepository.findAllByStatus(status, pageable).map(this::mapToResponse);
        }
        return researchRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public HealthSummaryResponse getHealthSummary() {
        long totalFacilities = facilityRepository.count();
        long totalResearch = researchRepository.count();

        long pending = facilityRepository.countByStatus(Status.PENDING) + researchRepository.countByStatus(Status.PENDING);
        long approved = facilityRepository.countByStatus(Status.APPROVED) + researchRepository.countByStatus(Status.APPROVED);
        long rejected = facilityRepository.countByStatus(Status.REJECTED) + researchRepository.countByStatus(Status.REJECTED);

        return HealthSummaryResponse.builder()
                .totalRegistered(totalFacilities + totalResearch)
                .pending(pending)
                .approved(approved)
                .rejected(rejected)
                .build();
    }

    private HealthRegistrationResponse mapToResponse(HealthFacility facility) {
        HealthRegistrationResponse response = new HealthRegistrationResponse();
        response.setId(facility.getId());
        response.setFacilityName(facility.getFacilityName());
        response.setFacilityType(facility.getFacilityType());
        response.setAddress(facility.getAddress());
        response.setPhone(facility.getPhone());
        response.setEmail(facility.getEmail());
        response.setOwnerName(facility.getOwnerName());
        response.setStatus(facility.getStatus().name());
        response.setPermitUrl(facility.getPermitUrl());
        response.setCreatedAt(facility.getCreatedAt());
        return response;
    }

    private HealthRegistrationResponse mapToResponse(ResearchEthicalApproval research) {
        HealthRegistrationResponse response = new HealthRegistrationResponse();
        response.setId(research.getId());
        response.setFacilityName(research.getProjectTitle());
        response.setFacilityType("Research");
        response.setAddress(research.getInstitution());
        response.setStatus(research.getStatus().name());
        response.setPermitUrl(research.getPermitUrl());
        response.setCreatedAt(research.getCreatedAt());
        return response;
    }

    private ApplicationStatus mapToTrackingStatus(Status status) {
        return switch (status) {
            case PENDING -> ApplicationStatus.PENDING;
            case UNDER_REVIEW -> ApplicationStatus.IN_REVIEW;
            case REVIEWED -> ApplicationStatus.ACTION_REQUIRED;
            case VERIFIED -> ApplicationStatus.IN_REVIEW;
            case APPROVED -> ApplicationStatus.APPROVED;
            case REJECTED -> ApplicationStatus.REJECTED;
            default -> ApplicationStatus.PENDING;
        };
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void createInspectionRequest(String name, String type, HealthFacility school) {
        InspectionRequest inspectionRequest = InspectionRequest.builder()
                .requestId(UUID.randomUUID())
                .sourceService("HEALTH")
                .applicantName(name)
                .applicationType(type)
                .status(Status.PENDING)
                .build();
        inspectionService.createInspection(inspectionRequest, school);
    }
}
