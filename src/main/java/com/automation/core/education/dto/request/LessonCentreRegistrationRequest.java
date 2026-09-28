package com.automation.core.education.dto.request;

import lombok.Data;

@Data
public class LessonCentreRegistrationRequest {
    private String centreName;
    private String subjectSpecialization;
    private String address;
    private String phone;
    private String email;
    private String ownerName;
}
