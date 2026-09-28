package com.automation.core.health.dto.request;

import lombok.Data;

@Data
public class ResearchEthicalApprovalRequest {
    private String projectTitle;
    private String principalInvestigator;
    private String institution;
}
