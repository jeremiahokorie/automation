package com.automation.core.education.service;

import com.automation.core.education.dto.request.LessonCentreRegistrationRequest;
import com.automation.core.education.dto.request.SchoolRegistrationRequest;
import com.automation.core.education.dto.response.EducationRegistrationResponse;
import com.automation.core.education.model.LessonCentre;
import com.automation.core.education.model.SchoolRegistration;
import com.automation.core.education.repository.LessonCentreRegistrationRepository;
import com.automation.core.education.repository.SchoolRegistrationRepository;
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
public class EducationServiceImpl implements EducationService {

    private final SchoolRegistrationRepository schoolRepository;
    private final LessonCentreRegistrationRepository lessonCentreRepository;
    private final ApplicationTrackingService trackingService;

    @Override
    @Transactional
    public EducationRegistrationResponse registerSchool(SchoolRegistrationRequest request, User user) {
        SchoolRegistration school = SchoolRegistration.builder()
                .schoolName(request.getSchoolName())
                .schoolType(request.getSchoolType())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .ownerName(request.getOwnerName())
                .createdBy(user)
                .status(Status.PENDING)
                .build();

        school = schoolRepository.save(school);
        trackingService.registerApplication("SCHOOL_REG", school.getId(), user);

        return mapToResponse(school);
    }

    @Override
    @Transactional
    public EducationRegistrationResponse registerLessonCentre(LessonCentreRegistrationRequest request, User user) {
        LessonCentre centre = LessonCentre.builder()
                .centreName(request.getCentreName())
                .subjectSpecialization(request.getSubjectSpecialization())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .ownerName(request.getOwnerName())
                .createdBy(user)
                .status(Status.PENDING)
                .build();

        centre = lessonCentreRepository.save(centre);
        trackingService.registerApplication("LESSON_CENTRE_REG", centre.getId(), user);
        return mapToResponse(centre);
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

    private EducationRegistrationResponse mapToResponse(SchoolRegistration school) {
        EducationRegistrationResponse response = new EducationRegistrationResponse();
        response.setId(school.getId());
        response.setName(school.getSchoolName());
        response.setStatus(school.getStatus().name());
        response.setPermitUrl(school.getPermitUrl());
        response.setCreatedAt(school.getCreatedAt());
        return response;
    }

    private EducationRegistrationResponse mapToResponse(LessonCentre centre) {
        EducationRegistrationResponse response = new EducationRegistrationResponse();
        response.setId(centre.getId());
        response.setName(centre.getCentreName());
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
}
