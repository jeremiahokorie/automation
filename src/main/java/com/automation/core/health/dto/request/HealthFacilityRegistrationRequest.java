package com.automation.core.health.dto.request;

import lombok.Data;

@Data
public class HealthFacilityRegistrationRequest {
    private String facilityName;
    private String facilityType;
    private String address;
    private String phone;
    private String email;
    private String ownerName;
}
