package com.automation.core.global.service.ServiceImpl;

import com.automation.core.global.dto.request.PermissionRequest;
import com.automation.core.global.dto.request.RolesRequest;
import com.automation.core.global.dto.response.PermissionResponse;
import com.automation.core.global.dto.response.RolesResponse;
import com.automation.core.global.model.Permission;
import com.automation.core.global.model.Roles;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.PermissionRepository;
import com.automation.core.global.repository.RoleRepository;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.global.service.UserService.AccessControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class AccessControlServiceImpl implements AccessControlService {
    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;
    private final UserRepository userRepo;


    @Override
    public RolesResponse createRole(RolesRequest request) {
        Roles role = new Roles();
        role.setName(request.getName());
        roleRepo.save(role);
        return RolesResponse.builder().name(request.getName()).build();
    }

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = new Permission();
        permission.setName(request.getName());
        permissionRepo.save(permission);
        return PermissionResponse.builder().name(request.getName()).build();
    }

    @Override
    public Roles assignPermissionToRole(Long roleId, Long permissionId) {
        Roles role = roleRepo.findById(roleId).orElseThrow();
        Permission permission = permissionRepo.findById(permissionId).orElseThrow();
        role.getPermissions().add(permission);
        return roleRepo.save(role);
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        User user = userRepo.findById(userId).orElseThrow();
        Roles role = roleRepo.findById(roleId).orElseThrow();
        user.getRoles().add(role);
        return userRepo.save(user);
    }
}
