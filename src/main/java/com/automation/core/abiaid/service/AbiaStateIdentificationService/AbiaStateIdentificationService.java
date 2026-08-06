package com.automation.core.abiaid.service.AbiaStateIdentificationService;

import com.automation.core.abiaid.dto.request.AbiaStateIdentificationRequest;
import com.automation.core.abiaid.dto.response.AbiaStateIdentificationResponse;

import java.util.List;

public interface AbiaStateIdentificationService {
    AbiaStateIdentificationResponse apply(AbiaStateIdentificationRequest request);
    List<AbiaStateIdentificationResponse> getMyApplications();
}
