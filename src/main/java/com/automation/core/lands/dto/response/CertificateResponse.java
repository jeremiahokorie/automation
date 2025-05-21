package com.automation.core.lands.dto.response;

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
public class CertificateResponse {
    private String applicantName;
    private String districtHeadLetter;
    private String salesAgreement;
    private String declarationOfAge;
    private String taxClearance;
    private String surveyData;
    private Status status;
    private String localGovernmentConfirmationLetter;
    private LocalDateTime createdAt = LocalDateTime.now();
}
