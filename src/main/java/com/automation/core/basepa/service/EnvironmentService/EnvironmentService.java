package com.automation.core.basepa.service.EnvironmentService;

import com.automation.core.basepa.dto.request.ApprovalRequest;
import com.automation.core.basepa.dto.request.EnvironmentRequest;
import com.automation.core.basepa.dto.request.PermitRenewRequest;
import com.automation.core.basepa.dto.response.ApprovalResponse;
import com.automation.core.basepa.dto.response.EnvironmentResponse;
import com.automation.core.basepa.dto.response.EnvironmentSummaryResponse;

import java.util.List;

public interface EnvironmentService {
    EnvironmentResponse apply(EnvironmentRequest environmentRequest);

    List<EnvironmentResponse> getAll();

    ApprovalResponse approveRequest(Long id, ApprovalRequest commentRequest);

    ApprovalResponse rejectRequest(Long id, ApprovalRequest commentRequest);

    EnvironmentResponse renewPermit(PermitRenewRequest permitRenewRequest);

    EnvironmentSummaryResponse getEnvironmentSummary();
}
