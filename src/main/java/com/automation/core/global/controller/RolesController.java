package com.automation.core.global.controller;

import com.automation.core.global.dto.request.RolesRequest;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.global.dto.response.RolesResponse;
import com.automation.core.global.service.UserService.RolesService;
import com.automation.util.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin(
        origins = {
                "http://localhost:5174"
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })
public class RolesController {
    private final RolesService rolesService;

    @PostMapping("/role")
    public ResponseEntity<AppResponse<RolesResponse>> createRole(@RequestBody RolesRequest roles) {
        RolesResponse role = rolesService.createRole(roles);
        return ResponseEntity.ok().body(AppResponse.<RolesResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(role).build());

    }
}
