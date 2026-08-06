package com.automation.core.abiaid.controller;

import com.automation.core.abiaid.dto.request.AbiaStateIdentificationRequest;
import com.automation.core.abiaid.dto.response.AbiaStateIdentificationResponse;
import com.automation.core.abiaid.service.AbiaStateIdentificationService.AbiaStateIdentificationService;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.util.constant.AppConstant;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/abia-id")
@RequiredArgsConstructor
public class AbiaStateIdentificationController {

    private final AbiaStateIdentificationService service;

    @PostMapping("/apply")
    @ApiOperation(value = "apply for abia state identification number", notes = "This endpoint allows users to apply for abia state identification number")
    public ResponseEntity<AppResponse<AbiaStateIdentificationResponse>> apply(
            @RequestBody AbiaStateIdentificationRequest request) {
        AbiaStateIdentificationResponse response = service.apply(request);
        AppResponse<AbiaStateIdentificationResponse> appResponse = AppResponse
                .<AbiaStateIdentificationResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value())
                .data(response)
                .error("")
                .build();
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @GetMapping("/my-applications")
    @ApiOperation(value = "get logged-in user's abia id applications", notes = "This endpoint returns applications created by the authenticated user")
    public ResponseEntity<AppResponse<List<AbiaStateIdentificationResponse>>> myApplications() {
        List<AbiaStateIdentificationResponse> responses = service.getMyApplications();
        AppResponse<List<AbiaStateIdentificationResponse>> appResponse = AppResponse
                .<List<AbiaStateIdentificationResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value())
                .data(responses)
                .error("")
                .build();
        return ResponseEntity.ok(appResponse);
    }
}
