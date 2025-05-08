package com.automation.core.lands.dto.response;

import lombok.Data;

@Data
public class StatutoryAllocationResponse {
    private String applicantName;
    private String email;
    private String taxClearanceUrl;
    private String declarationOfAgeUrl;
    private String formUrl;
}
