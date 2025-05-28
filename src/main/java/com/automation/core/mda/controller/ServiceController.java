package com.automation.core.mda.controller;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.mda.dto.request.ServiceRequest;
import com.automation.core.mda.dto.response.ServiceResponse;
import com.automation.core.mda.service.service.mdaService;
import com.automation.util.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/mda/")
@RequiredArgsConstructor
public class ServiceController {

    private final mdaService service;

    @PostMapping("/create")
    public ResponseEntity<AppResponse<ServiceResponse>> createService(@RequestBody ServiceRequest serviceRequest) {
        ServiceResponse serviceResponse = service.createService(serviceRequest);
        AppResponse<ServiceResponse> appResponse = AppResponse.<ServiceResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(serviceResponse).build();
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }
}
