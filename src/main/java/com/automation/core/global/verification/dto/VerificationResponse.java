package com.automation.core.global.verification.dto;

import lombok.*;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationResponse {
    private boolean verified;
    private String documentType; // e.g., "ABSIN", "BUSINESS", "LAND", "ENVIRONMENT"
    private String holderName;
    private String status;
    private Map<String, Object> details;
    private String message;
}
