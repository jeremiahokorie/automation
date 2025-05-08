package com.automation.core.lands.dto.response;

import lombok.Data;

@Data
public class CertificateResponse {
    private String applicantName;
    private String email;
    private String districtHeadLetterUrl;
    private String salesAgreementUrl;
    private String declarationOfAgeUrl;
    private String taxClearanceUrl;
    private String surveyDataUrl;
    private String localGovernmentConfirmationUrl;
    private String formUrl;
}
