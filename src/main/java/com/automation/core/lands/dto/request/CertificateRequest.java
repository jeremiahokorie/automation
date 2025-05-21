package com.automation.core.lands.dto.request;

import com.automation.util.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateRequest {
    private String applicantName;
    private String districtHeadLetter;
    private String salesAgreement;
    private String declarationOfAge;
    private String taxClearance;
    private String surveyData;
    private String status;
    private String applicantEmail;
    private String localGovernmentConfirmationLetter;
    private LocalDateTime createdAt = LocalDateTime.now();
}
