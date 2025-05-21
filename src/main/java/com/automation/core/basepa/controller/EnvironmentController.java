package com.automation.core.basepa.controller;

import com.automation.core.basepa.dto.request.EnvironmentRequest;
import com.automation.core.basepa.dto.response.EnvironmentResponse;
import com.automation.core.basepa.service.EnvironmentService.EnvironmentService;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.util.constant.AppConstant;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/environment")
@RequiredArgsConstructor
@CrossOrigin(
        origins = {
                "http://localhost:5174"},
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })
public class EnvironmentController {
    private final EnvironmentService environmentService;

    @PostMapping("/apply-permit")
    public ResponseEntity<AppResponse<EnvironmentResponse>> applyPermit(@RequestBody EnvironmentRequest environmentRequest) {
        EnvironmentResponse response = environmentService.apply(environmentRequest);
        AppResponse<EnvironmentResponse> appResponse = AppResponse.<EnvironmentResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(response).build();
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @GetMapping("/getPermits")
    public ResponseEntity<AppResponse<List<EnvironmentResponse>>> getPermits() {
        List<EnvironmentResponse> response = environmentService.getAll();
        return ResponseEntity.ok().body(AppResponse.<List<EnvironmentResponse>>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(response).error("").build()
        );
    }





}
