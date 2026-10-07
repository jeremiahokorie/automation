package com.automation.core.education.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EducationRegistrationResponse {
    private Long id;
    private String name;
    private String type;
    private String address;
    private String phone;
    private String email;
    private String ownerName;
    private String status;
    private String permitUrl;
    private LocalDateTime createdAt;
}

