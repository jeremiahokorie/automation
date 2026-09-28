package com.automation.core.health.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HealthRegistrationResponse {
    private Long id;
    private String facilityName;
    private String status;
    private String permitUrl;
    private LocalDateTime createdAt;
}
