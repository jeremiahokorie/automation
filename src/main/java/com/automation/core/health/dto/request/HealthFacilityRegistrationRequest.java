package com.automation.core.health.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HealthFacilityRegistrationRequest {
    @NotBlank(message = "Facility name is required")
    private String facilityName;

    @NotBlank(message = "Facility type is required")
    private String facilityType;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Owner name is required")
    private String ownerName;
}
