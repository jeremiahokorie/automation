package com.automation.core.global.controller;


import com.automation.core.basepa.service.EnvironmentService.EnvironmentService;
import com.automation.core.commerce.service.service.BusinessRegistrationService;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.global.dto.response.DashboardResponse;
import com.automation.core.global.service.UserService.DashboardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/dashboard")
@RequiredArgsConstructor
    public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardResponse> getDashboardSummary() {
        DashboardResponse summary = dashboardService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }

    }
