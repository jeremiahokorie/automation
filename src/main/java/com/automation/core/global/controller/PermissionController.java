package com.automation.core.global.controller;


import com.automation.core.global.dto.request.PermissionRequest;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.global.dto.response.PermissionResponse;
import com.automation.core.global.service.UserService.PermissionService;
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
public class PermissionController {
    private final PermissionService permissionService;


    @PostMapping("/addPermission")
    public ResponseEntity<AppResponse<PermissionResponse>> addPermission(@RequestBody PermissionRequest permission){
        PermissionResponse permissionResponse = permissionService.addPermission(permission);
        return ResponseEntity.ok().body(AppResponse.<PermissionResponse>builder()
                .message(AppConstant.ApiResponseMessage.SUCCESSFUL)
                .status(HttpStatus.OK.value()).data(permissionResponse).build());
    }

}
