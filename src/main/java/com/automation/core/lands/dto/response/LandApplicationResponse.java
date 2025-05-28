package com.automation.core.lands.dto.response;

import com.automation.util.enums.GlobalStatus;
import com.automation.util.enums.LandApplicationType;
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
public class LandApplicationResponse {
    private Long id;
    private String applicantName;
    private String email;
    private LandApplicationType applicationType;
    private Status status;
    private LocalDateTime applicationDate;
    private LocalDateTime approvalDate;
    private String documents;
    private String certificateUrl;

    private String districtHeadLetter;
    private String salesAgreement;
    private String declarationOfAge;
    private String taxClearance;
    private String surveyData;
    private String applicantEmail;
    private String localGovernmentConfirmationLetter;
    private LocalDateTime createdAt = LocalDateTime.now();

    private String passportPhotos;
    private String taxClearances;
    private String administrativeCharges;
    private String processingFees;


}
