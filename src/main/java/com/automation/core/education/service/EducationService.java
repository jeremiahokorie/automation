package com.automation.core.education.service;

import com.automation.core.education.dto.request.LessonCentreRegistrationRequest;
import com.automation.core.education.dto.request.SchoolRegistrationRequest;
import com.automation.core.education.dto.response.EducationRegistrationResponse;
import com.automation.core.global.model.User;
import com.automation.util.enums.Status;

public interface EducationService {
    EducationRegistrationResponse registerSchool(SchoolRegistrationRequest request, User user);
    EducationRegistrationResponse registerLessonCentre(LessonCentreRegistrationRequest request, User user);
    void updateSchoolStatus(Long id, Status status, String officer, String comment);
    void updateLessonCentreStatus(Long id, Status status, String officer, String comment);
    void approveSchool(Long id, String permitUrl);
}
