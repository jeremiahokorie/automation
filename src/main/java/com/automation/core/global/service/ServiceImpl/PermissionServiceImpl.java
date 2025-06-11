package com.automation.core.global.service.ServiceImpl;

import com.automation.core.global.dto.request.PermissionRequest;
import com.automation.core.global.dto.response.PermissionResponse;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.global.model.Permission;
import com.automation.core.global.repository.PermissionRepository;
import com.automation.core.global.service.UserService.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;



    @Override
    public PermissionResponse addPermission(PermissionRequest permission) {
        Optional<Permission> permissions = permissionRepository.findByname(permission.getName());

        if (permissions.isPresent()) {
            throw new ResourceNotFoundException("Permission already exist");
        }

        Permission permissionEntity = new Permission();
        permissionEntity.setName(permission.getName());
        permissionRepository.save(permissionEntity);

        return PermissionResponse.builder()
                .name(permission.getName())
                .build();
    }
}
