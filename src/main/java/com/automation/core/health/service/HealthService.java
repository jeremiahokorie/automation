package com.automation.core.health.service;

import com.automation.core.health.dto.request.HealthFacilityRegistrationRequest;
import com.automation.core.health.dto.request.ResearchEthicalApprovalRequest;
import com.automation.core.health.dto.response.HealthRegistrationResponse;
import com.automation.core.global.model.User;
import com.automation.util.enums.Status;

public interface HealthService {
    HealthRegistrationResponse registerFacility(HealthFacilityRegistrationRequest request, User user);
    HealthRegistrationResponse registerResearch(ResearchEthicalApprovalRequest request, User user);
    void updateFacilityStatus(Long id, Status status, String officer, String comment);
    void updateResearchStatus(Long id, Status status, String officer, String comment);
    void approveFacility(Long id, String permitUrl);
}
