package com.automation.core.global.service.ServiceImpl;

import com.automation.core.global.dto.request.RolesRequest;
import com.automation.core.global.dto.response.RolesResponse;
import com.automation.core.global.model.Roles;
import com.automation.core.global.repository.RoleRepository;
import com.automation.core.global.service.UserService.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RolesServiceImpl implements RolesService {

    private final RoleRepository roleRepository;


    @Override
    public RolesResponse createRole(RolesRequest roles) {
        Roles role = new Roles();
        role.setName(roles.getName());
        roleRepository.save(role);
        return RolesResponse.builder()
                .name(roles.getName())
                .value(role.getValue())
                .description(role.getDescription())
                .build();
    }
}
