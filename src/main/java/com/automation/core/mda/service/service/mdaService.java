package com.automation.core.mda.service.service;

import com.automation.core.mda.dto.request.mdaRequest;
import com.automation.core.mda.dto.response.mdaResponse;

import java.util.List;

public interface mdaService {
    
    mdaResponse createMda(mdaRequest mdaRequest);
    List<mdaResponse> getMdas();

    void deleteMdaByCode(String mdaCode);
}
