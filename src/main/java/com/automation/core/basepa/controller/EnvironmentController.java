package com.automation.core.basepa.controller;

import com.automation.core.basepa.dto.request.ApprovalRequest;
import com.automation.core.basepa.dto.request.EnvironmentRequest;
import com.automation.core.basepa.dto.request.PermitRenewRequest;
import com.automation.core.basepa.dto.response.ApprovalResponse;
import com.automation.core.basepa.dto.response.EnvironmentResponse;
import com.automation.core.basepa.service.EnvironmentService.EnvironmentService;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.util.constant.AppConstant;
import io.swagger.models.Response;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/environment")
@RequiredArgsConstructor
public class EnvironmentController {
    private final EnvironmentService environmentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/apply")
    public ResponseEntity<AppResponse<EnvironmentResponse>> applyPermit(@RequestBody EnvironmentRequest environmentRequest) {
        EnvironmentResponse response = environmentService.apply(environmentRequest);
        AppResponse<EnvironmentResponse> appResponse = AppResponse.<EnvironmentResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(response).build();
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/renew")
    public ResponseEntity<AppResponse<EnvironmentResponse>>renewal(@RequestBody PermitRenewRequest permitRenewRequest) {
        EnvironmentResponse businessRenewalResponse = environmentService.renewPermit(permitRenewRequest);
        AppResponse<EnvironmentResponse> response = AppResponse.<EnvironmentResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value()).data(businessRenewalResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getPermits")
    public ResponseEntity<AppResponse<List<EnvironmentResponse>>> getPermits() {
        List<EnvironmentResponse> response = environmentService.getAll();
        return ResponseEntity.ok().body(AppResponse.<List<EnvironmentResponse>>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(response).error("").build()
        );
    }

    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'ENVIRONMENT_OFFICER')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<AppResponse<ApprovalResponse>> approvePermit(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalRequest commentRequest) {

        ApprovalResponse approval = environmentService.approveRequest(id, commentRequest);

        AppResponse<ApprovalResponse> response = AppResponse.<ApprovalResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value())
                .data(approval)
                .error("")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'ENVIRONMENT_OFFICER')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<AppResponse<ApprovalResponse>> rejectBusiness(
            @PathVariable Long id,
            @RequestBody ApprovalRequest commentRequest) {

        ApprovalResponse reject = environmentService.rejectRequest(id, commentRequest);

        AppResponse<ApprovalResponse> response = AppResponse.<ApprovalResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value())
                .data(reject)
                .error("")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
