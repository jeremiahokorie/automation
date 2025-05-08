package com.automation.core.commerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRegistrationRequest {
    private Long id;
    private String businessName;
    private String ownerName;
    private String address;
    private String phone;
    private String email;
    private String status; // PENDING, APPROVED, ISSUED
    private LocalDate dateRegistered;
    private boolean isRenewal;
    private String businessNumber;
}
