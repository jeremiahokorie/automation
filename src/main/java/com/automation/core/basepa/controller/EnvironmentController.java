package com.automation.core.basepa.controller;

import com.automation.core.basepa.dto.request.ApprovalRequest;
import com.automation.core.basepa.dto.request.EnvironmentRequest;
import com.automation.core.basepa.dto.request.PermitRenewRequest;
import com.automation.core.basepa.dto.response.ApprovalResponse;
import com.automation.core.basepa.dto.response.EnvironmentResponse;
import com.automation.core.basepa.dto.response.EnvironmentSummaryResponse;
import com.automation.core.basepa.service.EnvironmentService.EnvironmentService;
import com.automation.core.commerce.dto.response.BusinessRegistrationResponse;
import com.automation.core.commerce.dto.response.BusinessSummaryResponse;
import com.automation.core.global.dto.response.AppResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.util.constant.AppConstant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/environment")
@RequiredArgsConstructor
public class EnvironmentController {
    private final EnvironmentService environmentService;

    @PostMapping("/apply")
    @ApiOperation(value = "apply for an environment permit",
            notes = "This endpoint allows users to apply for an environment permit")
    public ResponseEntity<AppResponse<EnvironmentResponse>> applyPermit(@RequestBody EnvironmentRequest environmentRequest) {
        EnvironmentResponse response = environmentService.apply(environmentRequest);
        AppResponse<EnvironmentResponse> appResponse = AppResponse.<EnvironmentResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(response).build();
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }


    @PutMapping("/renew")
    @ApiOperation(value = "renew environment permit",
            notes = "This endpoint renews an environment permit")
    public ResponseEntity<AppResponse<EnvironmentResponse>>renewal(@RequestBody PermitRenewRequest permitRenewRequest) {
        EnvironmentResponse businessRenewalResponse = environmentService.renewPermit(permitRenewRequest);
        AppResponse<EnvironmentResponse> response = AppResponse.<EnvironmentResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value()).data(businessRenewalResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/getPermits")
    @ApiOperation(value = "get all environment permits",
            notes = "This endpoint returns all environment permits")
    public ResponseEntity<AppResponse<List<EnvironmentResponse>>> getPermits() {
        List<EnvironmentResponse> response = environmentService.getAll();
        return ResponseEntity.ok().body(AppResponse.<List<EnvironmentResponse>>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(response).error("").build()
        );
    }


    @GetMapping("/paginated/getPermits")
    @ApiOperation(value = "get all environment permits with pagination",
            notes = "This endpoint returns all environment permits with pagination")
    public ResponseEntity<AppResponse<Page<EnvironmentResponse>>> getPermits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Page<EnvironmentResponse> responses = environmentService.getAllAppliedPermit(page, size, sortBy, sortDir);
        AppResponse<Page<EnvironmentResponse>> response = AppResponse.<Page<EnvironmentResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value())
                .data(responses)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/approve")
    @ApiOperation(value = "approve environment permit",
            notes = "This endpoint approves an environment permit by its ID")
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

    @PutMapping("/{id}/reject")
    @ApiOperation(value = "reject environment permit",
            notes = "This endpoint rejects an environment permit by its ID")
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

    @GetMapping("/environment-summary")
    @ApiOperation(value = "get environment summary", notes = "This endpoint returns the summary of environment permits")
    public ResponseEntity<EnvironmentSummaryResponse>summary(){
        EnvironmentSummaryResponse businessRegistrationResponse = environmentService.getEnvironmentSummary();
        return ResponseEntity.ok().body(businessRegistrationResponse);
    }

    @GetMapping("/paginated/getPermits/{offset}/{pageSize}")
    public ResponseEntity<AppResponse<Page<EnvironmentResponse>>> getPaginatedBusinesses(
            @PathVariable int offset,
            @PathVariable int pageSize) {
        int zeroBasedPage = offset > 0 ? offset - 1 : 0;
        Page<EnvironmentResponse> responses = environmentService.getPaginatedPermits(zeroBasedPage, pageSize);
        AppResponse<Page<EnvironmentResponse>> response = AppResponse.<Page<EnvironmentResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value())
                .data(responses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/permit")
    @ApiOperation(value = "download environment permit",
            notes = "This endpoint allows downloading the approved environmental permit")
    public ResponseEntity<Resource> downloadPermit(@PathVariable Long id) {
        String permitUrl = environmentService.getPermitUrl(id);
        Path path = Paths.get(permitUrl);
        try {
            Resource resource = new UrlResource(path.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName().toString() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("Permit file not found");
        }
    }
}
