package com.automation.core.education.service;

import com.automation.core.education.dto.request.LessonCentreRegistrationRequest;
import com.automation.core.education.dto.request.SchoolRegistrationRequest;
import com.automation.core.education.dto.response.EducationRegistrationResponse;
import com.automation.core.education.dto.response.EducationSummaryResponse;
import com.automation.core.global.model.User;
import com.automation.util.enums.Status;
import org.springframework.data.domain.Page;

public interface EducationService {
    EducationRegistrationResponse registerSchool(SchoolRegistrationRequest request);
    EducationRegistrationResponse registerLessonCentre(LessonCentreRegistrationRequest request);
    void updateSchoolStatus(Long id, Status status, String officer, String comment);
    void updateLessonCentreStatus(Long id, Status status, String officer, String comment);
    void approveSchool(Long id, String permitUrl);

    Page<EducationRegistrationResponse> getSchools(int page, int size, String sortBy, String sortDir, Status status);
    Page<EducationRegistrationResponse> getLessonCentres(int page, int size, String sortBy, String sortDir, Status status);
    EducationSummaryResponse getEducationSummary();
}
