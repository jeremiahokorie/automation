package com.automation.core.commerce.dto.response;

import com.automation.util.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BusinessRenewalResponse {
    private Long id;
    private String businessNumber;
    private String businessName;
    private Status status;
    private LocalDate renewalDate;
    private String comment;
    private String authorizationUrl;
}