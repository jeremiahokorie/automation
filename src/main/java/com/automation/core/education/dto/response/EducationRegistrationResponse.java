package com.automation.core.education.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EducationRegistrationResponse {
    private Long id;
    private String name;
    private String status;
    private String permitUrl;
    private LocalDateTime createdAt;
}
