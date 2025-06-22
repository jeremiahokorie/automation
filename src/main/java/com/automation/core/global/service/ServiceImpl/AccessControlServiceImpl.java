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
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        role.setValue(request.getValue());
        role.setDescription(request.getDescription());
        roleRepo.save(role);
        List<Permission> permissions = permissionRepo.findAllById(request.getPermissionIds());
        role.setPermissions(permissions);
        return RolesResponse.builder().id(role.getId()).name(request.getName()).value(request.getValue()).description(request.getDescription()).build();
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

    @Override
    public List<PermissionResponse> getPermissions() {
        List<Permission> permissions = permissionRepo.findAll();
        return permissions.stream().map(permissions1 ->
                PermissionResponse.builder()
                        .id(permissions1.getId())
                        .name(permissions1.getName())
                        .description(permissions1.getDescription())
                        .value(permissions1.getValue())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public List<RolesResponse> getRoles() {
        List<Roles> roleResp = roleRepo.findAll();
        return roleResp.stream().map(roles1 ->
                RolesResponse.builder()
                        .id(roles1.getId())
                        .name(roles1.getName())
                        .value(roles1.getValue())
                        .description(roles1.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public RolesResponse updateRole(Long id, RolesRequest request) {
        Roles role = roleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setName(request.getName());
        role.setValue(request.getValue());
        role.setDescription(request.getDescription());

        List<Permission> permissions  = permissionRepo.findAllById(request.getPermissionIds());
        role.setPermissions(permissions);

        roleRepo.save(role);
        return RolesResponse.builder().name(request.getName())
                .id(role.getId())
                .value(role.getValue())
                .description(role.getDescription())
                .build();

    }
}
