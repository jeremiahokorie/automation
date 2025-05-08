package com.automation.core.lands.service.service;

import com.automation.core.lands.dto.request.LandApplicationRequest;
import com.automation.core.lands.dto.response.LandApplicationResponse;

public interface LandApplicationService {

    LandApplicationResponse applyForLand(LandApplicationRequest landApplicationRequest);
}
