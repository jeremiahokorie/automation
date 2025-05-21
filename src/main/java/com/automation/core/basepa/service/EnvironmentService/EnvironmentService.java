package com.automation.core.basepa.service.EnvironmentService;

import com.automation.core.basepa.dto.request.EnvironmentRequest;
import com.automation.core.basepa.dto.response.EnvironmentResponse;

import java.util.List;

public interface EnvironmentService {
    EnvironmentResponse apply(EnvironmentRequest environmentRequest);

    List<EnvironmentResponse> getAll();
}
