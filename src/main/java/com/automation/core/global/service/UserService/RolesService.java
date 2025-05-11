package com.automation.core.global.service.UserService;

import com.automation.core.global.dto.request.RolesRequest;
import com.automation.core.global.dto.response.RolesResponse;

public interface RolesService {
    RolesResponse createRole(RolesRequest roles);
}
