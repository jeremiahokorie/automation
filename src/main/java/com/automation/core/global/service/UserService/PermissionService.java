package com.automation.core.global.service.UserService;


import com.automation.core.global.dto.request.PermissionRequest;
import com.automation.core.global.dto.response.PermissionResponse;

public interface PermissionService {
    PermissionResponse addPermission(PermissionRequest permission);
}
