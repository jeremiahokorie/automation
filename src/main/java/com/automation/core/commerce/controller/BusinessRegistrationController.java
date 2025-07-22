package com.automation.core.commerce.controller;

import com.automation.core.commerce.dto.request.ApprovalandRejectRequest;
import com.automation.core.commerce.dto.request.BusinessRegistrationRequest;
import com.automation.core.commerce.dto.request.BusinessRenewalRequest;
import com.automation.core.commerce.dto.request.BusinessTypeRequest;
import com.automation.core.commerce.dto.response.*;
import com.automation.core.commerce.service.service.BusinessRegistrationService;
import com.automation.core.commerce.service.service.BusinessTypeService;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.util.constant.AppConstant;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/business")
@RequiredArgsConstructor
public class BusinessRegistrationController {
    private final BusinessRegistrationService businessRegistrationService;
    private final BusinessTypeService businessTypeService;

  // @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    @ApiOperation(value = "register a new business ",
            notes = "This endpoint registers a business")
    public ResponseEntity<AppResponse<BusinessRegistrationResponse>> registerBusiness(@RequestBody BusinessRegistrationRequest businessRegistrationRequest) {
        BusinessRegistrationResponse businessRegistrationResponse = businessRegistrationService.register(businessRegistrationRequest);
        AppResponse<BusinessRegistrationResponse> response = AppResponse.<BusinessRegistrationResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(businessRegistrationResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/businesses")
    @ApiOperation(value = "get all registered business ",
            notes = "This endpoint returns all registered business")
   // @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AppResponse<List<BusinessRegistrationResponse>>> getBusinessRegistration() {
        List<BusinessRegistrationResponse> response = businessRegistrationService.getRegisteredBusiness();
        return ResponseEntity.ok().body(AppResponse.<List<BusinessRegistrationResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(response).error("").build());
    }

   // @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'COMMISSIONER')")
    @PutMapping("/{businessNumber}/verify")
    public ResponseEntity<AppResponse<BusinessRegistrationResponse>> verifyBusiness(@PathVariable String businessNumber) {
        BusinessRegistrationResponse response = businessRegistrationService.verifyBusiness(businessNumber);
        AppResponse<BusinessRegistrationResponse>verify = AppResponse.<BusinessRegistrationResponse>builder()
                .message(AppConstant.ApiResponseMessage.SUCCESSFUL)
                .status(HttpStatus.OK.value()).data(response).error("").build();
        return new ResponseEntity<>(verify, HttpStatus.OK);
    }

    @PostMapping("/renewals")
    public ResponseEntity<AppResponse<BusinessRenewalResponse>>renewal(@RequestBody BusinessRenewalRequest businessRenewalRequest) {
        BusinessRenewalResponse businessRenewalResponse = businessRegistrationService.renewBusiness(businessRenewalRequest);
        AppResponse<BusinessRenewalResponse> response = AppResponse.<BusinessRenewalResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value()).data(businessRenewalResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{businessNumber}/approve")
    public ResponseEntity<AppResponse<ApprovalandRejectResponse>> approveBusiness(
            @PathVariable String businessNumber,
            @Valid @RequestBody ApprovalandRejectRequest commentRequest) {
        ApprovalandRejectResponse businessRenewalResponse = businessRegistrationService.approveRequest(businessNumber, commentRequest);

        AppResponse<ApprovalandRejectResponse> response = AppResponse.<ApprovalandRejectResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value())
                .data(businessRenewalResponse)
                .error("")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{businessNumber}/reject")
    public ResponseEntity<AppResponse<ApprovalandRejectResponse>> rejectBusiness(
            @PathVariable String businessNumber,
            @RequestBody ApprovalandRejectRequest commentRequest) {

        ApprovalandRejectResponse businessRenewalResponse = businessRegistrationService.rejectRequest(businessNumber, commentRequest);

        AppResponse<ApprovalandRejectResponse> response = AppResponse.<ApprovalandRejectResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value())
                .data(businessRenewalResponse)
                .error("")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/businessType")
    public ResponseEntity<AppResponse<BusinessTypeResponse>> createBusinessTypes(@RequestBody BusinessTypeRequest businessTypeRequest) {
        BusinessTypeResponse businessTypeResponse = businessTypeService.createBusinessType(businessTypeRequest);
        AppResponse<BusinessTypeResponse> response = AppResponse.<BusinessTypeResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(businessTypeResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/businessTpes")
    public ResponseEntity<AppResponse<List<BusinessTypeResponse>>> getBusinessTypes() {
        List<BusinessTypeResponse> businesses = businessTypeService.getAllBusiness();
        return ResponseEntity.ok().body(AppResponse.<List<BusinessTypeResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(businesses).error("").build());
    }

    @GetMapping("/business-summary")
    public ResponseEntity<BusinessSummaryResponse>summary(){
        BusinessSummaryResponse businessRegistrationResponse = businessRegistrationService.getBusinessSummary();
        return ResponseEntity.ok().body(businessRegistrationResponse);
    }


}
