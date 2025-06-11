package com.automation.core.global.controller;


import com.automation.core.global.dto.request.PermissionRequest;
import com.automation.core.global.dto.request.RolesRequest;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.global.dto.response.PermissionResponse;
import com.automation.core.global.dto.response.RolesResponse;
import com.automation.core.global.dto.response.UserResponse;
import com.automation.core.global.model.Permission;
import com.automation.core.global.model.Roles;
import com.automation.core.global.model.User;
import com.automation.core.global.service.UserService.AccessControlService;
import com.automation.util.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/auth/access")
@RequiredArgsConstructor
public class AccessControlController {
    private final AccessControlService accessControlService;

    @PostMapping("/roles")
    public ResponseEntity<AppResponse<RolesResponse>> createRole(@RequestParam RolesRequest request) {
        RolesResponse rolesResponse = accessControlService.createRole(request);
        AppResponse<RolesResponse> response = AppResponse.<RolesResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(rolesResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/permissions")
    public ResponseEntity<AppResponse<PermissionResponse>> createPermission(@RequestParam PermissionRequest request) {
        PermissionResponse permissionResponse =  accessControlService.createPermission(request);
        AppResponse<PermissionResponse> response = AppResponse.<PermissionResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(permissionResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/roles/{roleId}/permissions/{permissionId}")
    public Roles assignPermissionToRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        return accessControlService.assignPermissionToRole(roleId, permissionId);
    }

    @PostMapping("/users/{userId}/roles/{roleId}")
    public User assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        return accessControlService.assignRoleToUser(userId, roleId);
    }
}
