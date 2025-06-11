package com.automation.core.global.service.UserService;


import com.automation.core.global.dto.request.PermissionRequest;
import com.automation.core.global.dto.request.RolesRequest;
import com.automation.core.global.dto.response.PermissionResponse;
import com.automation.core.global.dto.response.RolesResponse;
import com.automation.core.global.model.Permission;
import com.automation.core.global.model.Roles;
import com.automation.core.global.model.User;

public interface AccessControlService {
    RolesResponse createRole(RolesRequest request);

    PermissionResponse createPermission(PermissionRequest request);

    Roles assignPermissionToRole(Long roleId, Long permissionId);

    User assignRoleToUser(Long userId, Long roleId);
}
