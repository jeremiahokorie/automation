package com.automation.core.commerce.controller;

import com.automation.core.commerce.dto.request.ApprovalandRejectRequest;
import com.automation.core.commerce.dto.request.BusinessRegistrationRequest;
import com.automation.core.commerce.dto.request.BusinessRenewalRequest;
import com.automation.core.commerce.dto.request.BusinessTypeRequest;
import com.automation.core.commerce.dto.response.*;
import com.automation.core.commerce.service.service.BusinessRegistrationService;
import com.automation.core.commerce.service.service.BusinessTypeService;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.global.dto.response.UserResponse;
import com.automation.core.global.model.User;
import com.automation.util.constant.AppConstant;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/business")
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
                //.recordCount(response.size())
                .status(HttpStatus.OK.value()).data(response).error("").build());
    }


    @GetMapping("/paginated/businesses")
    @ApiOperation(value = "get all registered business with pagination",
            notes = "This endpoint returns all registered business with pagination")
    public ResponseEntity<AppResponse<Page<BusinessRegistrationResponse>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Page<BusinessRegistrationResponse> responses = businessRegistrationService.getAllRegisteredBusiness(page, size, sortBy, sortDir);
        AppResponse<Page<BusinessRegistrationResponse>> response = AppResponse.<Page<BusinessRegistrationResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value())
               // .recordCount(responses.getSize())
                .data(responses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated/businesses/{offset}/{pageSize}")
    public ResponseEntity<AppResponse<Page<BusinessRegistrationResponse>>> getPaginatedBusinesses(
            @PathVariable int offset,
            @PathVariable int pageSize) {
        int zeroBasedPage = offset > 0 ? offset - 1 : 0;
        Page<BusinessRegistrationResponse> responses = businessRegistrationService.getPaginatedBusinesses(zeroBasedPage, pageSize);
        AppResponse<Page<BusinessRegistrationResponse>> response = AppResponse.<Page<BusinessRegistrationResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                //.recordCount(responses.getSize())
                .status(HttpStatus.OK.value())
                .data(responses)
                .build();
        return ResponseEntity.ok(response);
    }

   // @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'COMMISSIONER')")
    @PutMapping("/{businessNumber}/verify")
    @ApiOperation(value = "verify a business",
            notes = "This endpoint verifies a business by its business number")
    public ResponseEntity<AppResponse<BusinessRegistrationResponse>> verifyBusiness(@PathVariable String businessNumber) {
        BusinessRegistrationResponse response = businessRegistrationService.verifyBusiness(businessNumber);
        AppResponse<BusinessRegistrationResponse>verify = AppResponse.<BusinessRegistrationResponse>builder()
                .message(AppConstant.ApiResponseMessage.SUCCESSFUL)
                .status(HttpStatus.OK.value()).data(response).error("").build();
        return new ResponseEntity<>(verify, HttpStatus.OK);
    }

    @PostMapping("/renewals")
    @ApiOperation(value = "renew a business",
            notes = "This endpoint renews a business")
    public ResponseEntity<AppResponse<BusinessRenewalResponse>>renewal(@RequestBody BusinessRenewalRequest businessRenewalRequest) {
        BusinessRenewalResponse businessRenewalResponse = businessRegistrationService.renewBusiness(businessRenewalRequest);
        AppResponse<BusinessRenewalResponse> response = AppResponse.<BusinessRenewalResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value()).data(businessRenewalResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{businessNumber}/approve")
    @ApiOperation(value = "approve a business",
            notes = "This endpoint approves a business by its business number")
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
    @ApiOperation(value = "reject a business",
            notes = "This endpoint rejects a business by its business number")
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
    @ApiOperation(value = "create a new business type",
            notes = "This endpoint creates a new business type")
    public ResponseEntity<AppResponse<BusinessTypeResponse>> createBusinessTypes(@RequestBody BusinessTypeRequest businessTypeRequest) {
        BusinessTypeResponse businessTypeResponse = businessTypeService.createBusinessType(businessTypeRequest);
        AppResponse<BusinessTypeResponse> response = AppResponse.<BusinessTypeResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(businessTypeResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/businessTpes")
    @ApiOperation(value = "get all business types",
            notes = "This endpoint returns all business types")
    public ResponseEntity<AppResponse<List<BusinessTypeResponse>>> getBusinessTypes() {
        List<BusinessTypeResponse> businesses = businessTypeService.getAllBusiness();
        return ResponseEntity.ok().body(AppResponse.<List<BusinessTypeResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                //.recordCount(businesses.size())
                .status(HttpStatus.OK.value()).data(businesses).error("").build());
    }

    @GetMapping("/business-summary")
    @ApiOperation(value = "get business summary",
            notes = "This endpoint returns a summary of business registrations")
    public ResponseEntity<AppResponse<BusinessSummaryResponse>>summary(){
        BusinessSummaryResponse businessRegistrationResponse = businessRegistrationService.getBusinessSummary();
        AppResponse<BusinessSummaryResponse> response = AppResponse.<BusinessSummaryResponse>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value())
                .data(businessRegistrationResponse)
                .error("")
                .build();
        return ResponseEntity.ok(response);

    }


}
