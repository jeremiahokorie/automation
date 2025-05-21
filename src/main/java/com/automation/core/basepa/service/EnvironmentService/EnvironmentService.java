package com.automation.core.basepa.service.EnvironmentService;

import com.automation.core.basepa.dto.request.EnvironmentRequest;
import com.automation.core.basepa.dto.response.EnvironmentResponse;

public interface EnvironmentService {
    EnvironmentResponse apply(EnvironmentRequest environmentRequest);
}
