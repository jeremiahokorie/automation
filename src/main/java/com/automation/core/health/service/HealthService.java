package com.automation.core.health.service;

import com.automation.core.health.dto.request.HealthFacilityRegistrationRequest;
import com.automation.core.health.dto.request.ResearchEthicalApprovalRequest;
import com.automation.core.health.dto.response.HealthRegistrationResponse;
import com.automation.core.health.dto.response.HealthSummaryResponse;
import com.automation.core.global.model.User;
import com.automation.util.enums.Status;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface HealthService {
    HealthRegistrationResponse registerFacility(HealthFacilityRegistrationRequest request);
    HealthRegistrationResponse registerResearch(ResearchEthicalApprovalRequest request);
    void updateFacilityStatus(Long id, Status status, String officer, String comment);
    void updateResearchStatus(Long id, Status status, String officer, String comment);
    void approveFacility(Long id, String permitUrl);

    Page<HealthRegistrationResponse> getFacilities(int page, int size, String sortBy, String sortDir, Status status);
    Page<HealthRegistrationResponse> getResearchApplications(int page, int size, String sortBy, String sortDir, Status status);
    HealthSummaryResponse getHealthSummary();
}
