package com.automation.core.mda.service.service;

import com.automation.core.mda.dto.request.mdaRequest;
import com.automation.core.mda.dto.response.mdaResponse;
import com.automation.core.mda.model.ServicesModel;

import java.util.List;

public interface mdaService {

    mdaResponse createMdaWithServices(String mdaName, List<String> services);

    mdaResponse createMda(mdaRequest mdaRequest);
    List<mdaResponse> getMdas();

    void deleteMdaByCode(String mdaCode);

    List<ServicesModel> getServicesByMda(Long id);
}
