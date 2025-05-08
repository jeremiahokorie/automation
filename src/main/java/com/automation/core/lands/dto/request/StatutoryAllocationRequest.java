package com.automation.core.lands.dto.request;

import lombok.Data;

@Data
public class StatutoryAllocationRequest {
    private String applicantName;
    private String email;
    private String taxClearanceUrl;
    private String declarationOfAgeUrl;
    private String formUrl;

}
