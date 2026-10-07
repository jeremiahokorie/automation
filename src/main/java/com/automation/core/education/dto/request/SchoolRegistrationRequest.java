package com.automation.core.education.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SchoolRegistrationRequest {
    @NotBlank(message = "School name is required")
    private String schoolName;

    @NotBlank(message = "School type is required")
    private String schoolType;

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
