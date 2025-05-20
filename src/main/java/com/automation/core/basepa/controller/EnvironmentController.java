package com.automation.core.basepa.controller;

import com.automation.core.basepa.dto.request.EnvironmentRequest;
import com.automation.core.basepa.dto.response.EnvironmentResponse;
import com.automation.core.basepa.service.EnvironmentService.EnvironmentService;
import com.automation.core.global.dto.response.AppResponse;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/environment")
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
public class EnvironmentController {
    private final EnvironmentService environmentService;

//    public ResponseEntity<AppResponse<EnvironmentResponse>> applyPermit(@RequestBody EnvironmentRequest environmentRequest) {
//
//    }



}
