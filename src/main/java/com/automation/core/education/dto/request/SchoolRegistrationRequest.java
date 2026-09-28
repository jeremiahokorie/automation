package com.automation.core.education.dto.request;

import lombok.Data;

@Data
public class SchoolRegistrationRequest {
    private String schoolName;
    private String schoolType;
    private String address;
    private String phone;
    private String email;
    private String ownerName;
}
