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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/auth/access")
@RequiredArgsConstructor
public class AccessControlController {
    private final AccessControlService accessControlService;

//    @PostMapping
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
//    public ResponseEntity<Role> createRole(@RequestBody RoleRequest request) {
//        Role createdRole = roleService.createRole(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
//    }



    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping("/roles")
    public ResponseEntity<AppResponse<RolesResponse>> createRole(@RequestBody RolesRequest request) {
        RolesResponse rolesResponse = accessControlService.createRole(request);
        AppResponse<RolesResponse> response = AppResponse.<RolesResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(rolesResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping("/permissions")
    public ResponseEntity<AppResponse<PermissionResponse>> createPermission(@RequestBody PermissionRequest request) {
        PermissionResponse permissionResponse =  accessControlService.createPermission(request);
        AppResponse<PermissionResponse> response = AppResponse.<PermissionResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(permissionResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping("/roles/{roleId}/permissions/{permissionId}")
    public Roles assignPermissionToRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        return accessControlService.assignPermissionToRole(roleId, permissionId);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping("/users/{userId}/roles/{roleId}")
    public User assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        return accessControlService.assignRoleToUser(userId, roleId);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping("/permissions")
    public ResponseEntity<AppResponse<List<PermissionResponse>>>Permissions(){
        List<PermissionResponse> permission = accessControlService.getPermissions();
        return ResponseEntity.ok().body(AppResponse.<List<PermissionResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(permission).build());
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping("/roles")
    public ResponseEntity<AppResponse<List<RolesResponse>>>Roles(){
        List<RolesResponse> roless = accessControlService.getRoles();
        return ResponseEntity.ok().body(AppResponse.<List<RolesResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(roless).build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<AppResponse<RolesResponse>> updateRole(@PathVariable Long id, @RequestBody RolesRequest request) {
        RolesResponse updatedRole = accessControlService.updateRole(id, request);
        return ResponseEntity.ok().body(AppResponse.<RolesResponse>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(updatedRole).build());

    }

}
