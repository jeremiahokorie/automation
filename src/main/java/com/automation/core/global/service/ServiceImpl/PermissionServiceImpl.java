package com.automation.core.global.service.ServiceImpl;

import com.automation.core.global.dto.request.PermissionRequest;
import com.automation.core.global.dto.response.PermissionResponse;
import com.automation.core.global.exception.CustomException;
import com.automation.core.global.model.Permission;
import com.automation.core.global.repository.PermissionRepository;
import com.automation.core.global.service.UserService.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;



    @Override
    public PermissionResponse addPermission(PermissionRequest permission) {
        Optional<Permission> permissions = permissionRepository.findBycode(permission.getCode());

        if (!permissions.isEmpty()) {
            throw new CustomException("Permission already exist");
        }

        Permission permissionEntity = new Permission();
        permissionEntity.setName(permission.getName());
        permissionEntity.setDescription(permission.getDescription());
        permissionEntity.setCode(permission.getCode());
        permissionRepository.save(permissionEntity);

        return PermissionResponse.builder()
                .name(permission.getName())
                .description(permission.getDescription())
                .code(permission.getCode())
                .build();
    }
}
