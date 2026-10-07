package com.automation.core.health.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HealthRegistrationResponse {
    private Long id;
    private String facilityName;
    private String facilityType;
    private String address;
    private String phone;
    private String email;
    private String ownerName;
    private String status;
    private String permitUrl;
    private LocalDateTime createdAt;
}

