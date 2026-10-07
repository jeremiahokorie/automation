package com.automation.core.education.service;

import com.automation.core.education.dto.request.LessonCentreRegistrationRequest;
import com.automation.core.education.dto.request.SchoolRegistrationRequest;
import com.automation.core.education.dto.response.EducationRegistrationResponse;
import com.automation.core.education.dto.response.EducationSummaryResponse;
import com.automation.core.education.model.LessonCentre;
import com.automation.core.education.model.SchoolRegistration;
import com.automation.core.education.repository.LessonCentreRegistrationRepository;
import com.automation.core.education.repository.SchoolRegistrationRepository;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.inspection.dto.request.InspectionRequest;
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
public class EducationServiceImpl implements EducationService {

    private final SchoolRegistrationRepository schoolRepository;
    private final LessonCentreRegistrationRepository lessonCentreRepository;
    private final ApplicationTrackingService trackingService;
    private final UserRepository userRepository;
    private final InspectionService inspectionService;

    @Override
    @Transactional
    public EducationRegistrationResponse registerSchool(SchoolRegistrationRequest request) {
        try {
            SchoolRegistration school = SchoolRegistration.builder()
                    .schoolName(request.getSchoolName())
                    .schoolType(request.getSchoolType())
                    .address(request.getAddress())
                    .phone(request.getPhone())
                    .email(request.getEmail())
                    .ownerName(request.getOwnerName())
                    .createdBy(getCurrentUser())
                    .status(Status.PENDING)
                    .build();

            school = schoolRepository.save(school);
            trackingService.registerApplication("SCHOOL_REG", school.getId(), getCurrentUser());


            // Trigger Inspection
            createInspectionRequest(school.getSchoolName(), "SCHOOL", school);
            return mapToResponse(school);

        } catch (Exception e) {
            log.error("Error occurred while registering school facility: {}", e.getMessage(), e);
            throw new RuntimeException("Submission failed due to a system error. Please try again later.");
        }
    }

    @Override
    @Transactional
    public EducationRegistrationResponse registerLessonCentre(LessonCentreRegistrationRequest request) {
        LessonCentre centre = LessonCentre.builder()
                .centreName(request.getCentreName())
                .subjectSpecialization(request.getSubjectSpecialization())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .ownerName(request.getOwnerName())
                .createdBy(getCurrentUser())
                .status(Status.PENDING)
                .build();

        centre = lessonCentreRepository.save(centre);
        trackingService.registerApplication("LESSON_CENTRE_REG", centre.getId(), getCurrentUser());

        // Trigger Inspection
        createInspectionRequest(centre.getCentreName(), "LESSON_CENTRE", centre);


        return mapToResponse(centre);
    }

    private void createInspectionRequest(String name, String type, SchoolRegistration school) {
        InspectionRequest inspectionRequest = InspectionRequest.builder()
                .requestId(UUID.randomUUID())
                .sourceService("EDUCATION")
                .applicantName(name)
                .applicationType(type)
                .status(Status.PENDING)
                .build();
        inspectionService.createInspection(inspectionRequest, school);
    }

    private void createInspectionRequest(String name, String type, LessonCentre centre) {
        InspectionRequest inspectionRequest = InspectionRequest.builder()
                .requestId(UUID.randomUUID())
                .sourceService("EDUCATION")
                .applicantName(name)
                .applicationType(type)
                .status(Status.PENDING)
                .build();
        inspectionService.createInspection(inspectionRequest, centre);
    }


    @Override
    @Transactional
    public void updateSchoolStatus(Long id, Status status, String officer, String comment) {
        SchoolRegistration school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        school.setStatus(status);
        schoolRepository.save(school);

        ApplicationStatus trackingStatus = mapToTrackingStatus(status);
        trackingService.updateStatusByApplicationId(id, trackingStatus, status.name(), officer, comment);
    }

    @Override
    @Transactional
    public void updateLessonCentreStatus(Long id, Status status, String officer, String comment) {
        LessonCentre centre = lessonCentreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson centre not found"));
        centre.setStatus(status);
        lessonCentreRepository.save(centre);

        ApplicationStatus trackingStatus = mapToTrackingStatus(status);
        trackingService.updateStatusByApplicationId(id, trackingStatus, status.name(), officer, comment);
    }

    @Override
    @Transactional
    public void approveSchool(Long id, String permitUrl) {
        SchoolRegistration school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        school.setStatus(Status.APPROVED);
        school.setPermitUrl(permitUrl);
        school.setPermitGeneratedDate(LocalDateTime.now());
        schoolRepository.save(school);
        trackingService.updateStatusByApplicationId(id, ApplicationStatus.APPROVED, "APPROVED", "System", "Licence issued successfully");
    }

    @Override
    public Page<EducationRegistrationResponse> getSchools(int page, int size, String sortBy, String sortDir, Status status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (status != null) {
            return schoolRepository.findAllByStatus(status, pageable).map(this::mapToResponse);
        }
        return schoolRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Page<EducationRegistrationResponse> getLessonCentres(int page, int size, String sortBy, String sortDir, Status status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (status != null) {
            return lessonCentreRepository.findAllByStatus(status, pageable).map(this::mapToResponse);
        }
        return lessonCentreRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public EducationSummaryResponse getEducationSummary() {
        long totalSchools = schoolRepository.count();
        long totalCentres = lessonCentreRepository.count();

        long pending = schoolRepository.countByStatus(Status.PENDING) + lessonCentreRepository.countByStatus(Status.PENDING);
        long approved = schoolRepository.countByStatus(Status.APPROVED) + lessonCentreRepository.countByStatus(Status.APPROVED);
        long rejected = schoolRepository.countByStatus(Status.REJECTED) + lessonCentreRepository.countByStatus(Status.REJECTED);

        return EducationSummaryResponse.builder()
                .totalRegistered(totalSchools + totalCentres)
                .pending(pending)
                .approved(approved)
                .rejected(rejected)
                .build();
    }

    private EducationRegistrationResponse mapToResponse(SchoolRegistration school) {
        EducationRegistrationResponse response = new EducationRegistrationResponse();
        response.setId(school.getId());
        response.setName(school.getSchoolName());
        response.setType(school.getSchoolType());
        response.setAddress(school.getAddress());
        response.setPhone(school.getPhone());
        response.setEmail(school.getEmail());
        response.setOwnerName(school.getOwnerName());
        response.setStatus(school.getStatus().name());
        response.setPermitUrl(school.getPermitUrl());
        response.setCreatedAt(school.getCreatedAt());
        return response;
    }

    private EducationRegistrationResponse mapToResponse(LessonCentre centre) {
        EducationRegistrationResponse response = new EducationRegistrationResponse();
        response.setId(centre.getId());
        response.setName(centre.getCentreName());
        response.setType(centre.getSubjectSpecialization());
        response.setAddress(centre.getAddress());
        response.setPhone(centre.getPhone());
        response.setEmail(centre.getEmail());
        response.setOwnerName(centre.getOwnerName());
        response.setStatus(centre.getStatus().name());
        response.setPermitUrl(centre.getPermitUrl());
        response.setCreatedAt(centre.getCreatedAt());
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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
