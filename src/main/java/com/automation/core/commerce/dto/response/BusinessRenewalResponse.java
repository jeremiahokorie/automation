package com.automation.core.commerce.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BusinessRenewalResponse {
    private String businessNumber;
    private String businessName;
    private String status;
    private LocalDate renewalDate;
    private String comment;
}