package com.automation.core.commerce.controller;

import com.automation.core.commerce.dto.request.BusinessRegistrationRequest;
import com.automation.core.commerce.dto.request.BusinessRenewalRequest;
import com.automation.core.commerce.dto.response.BusinessRegistrationResponse;
import com.automation.core.commerce.dto.response.BusinessRenewalResponse;
import com.automation.core.commerce.service.service.BusinessRegistrationService;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.util.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/commerce/")
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
public class BusinessRegistrationController {
    private final BusinessRegistrationService businessRegistrationService;

    @PostMapping("registration")
    public ResponseEntity<AppResponse<BusinessRegistrationResponse>> registerBusiness(@RequestBody BusinessRegistrationRequest businessRegistrationRequest) {
        BusinessRegistrationResponse businessRegistrationResponse = businessRegistrationService.register(businessRegistrationRequest);
        AppResponse<BusinessRegistrationResponse> response = AppResponse.<BusinessRegistrationResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(businessRegistrationResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/businesses")
    public ResponseEntity<AppResponse<List<BusinessRegistrationResponse>>> getBusinessRegistration() {
        List<BusinessRegistrationResponse> response = businessRegistrationService.getRegisteredBusiness();
        return ResponseEntity.ok().body(AppResponse.<List<BusinessRegistrationResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(response).error("").build());
    }

    @GetMapping("/{businessNumber}/verify")
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
    public ResponseEntity<AppResponse<BusinessRenewalResponse>> approveBusiness(
            @PathVariable String businessNumber,
            @RequestBody BusinessRenewalRequest commentRequest) {

        BusinessRenewalResponse businessRenewalResponse = businessRegistrationService.approveRequest(businessNumber, commentRequest);

        AppResponse<BusinessRenewalResponse> response = AppResponse.<BusinessRenewalResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value())
                .data(businessRenewalResponse)
                .error("")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @PutMapping("/{businessNumber}/reject")
    public ResponseEntity<AppResponse<BusinessRenewalResponse>> rejectBusiness(
            @PathVariable String businessNumber,
            @RequestBody BusinessRenewalRequest commentRequest) {

        BusinessRenewalResponse businessRenewalResponse = businessRegistrationService.rejectRequest(businessNumber, commentRequest);

        AppResponse<BusinessRenewalResponse> response = AppResponse.<BusinessRenewalResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value())
                .data(businessRenewalResponse)
                .error("")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
