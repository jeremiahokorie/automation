package com.automation.core.global.controller;


import com.automation.core.basepa.service.EnvironmentService.EnvironmentService;
import com.automation.core.commerce.service.service.BusinessRegistrationService;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.global.service.UserService.DashboardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/dashboard")
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
    public class DashboardController {
    private final DashboardService dashboardService;
    private final EnvironmentService environmentService;
    private final BusinessRegistrationService businessRegistrationService;

    @GetMapping("/total-approved-cofo")
    public ResponseEntity<Integer> totalApprovedCofO() {
        return ResponseEntity.ok(dashboardService.getTotalApprovedCofO());
    }

    @GetMapping("/total-rejected")
    public ResponseEntity<Integer> totalRejectedCofO() {
        return ResponseEntity.ok(dashboardService.getTotalRejectedCofO());
    }

    @GetMapping("/total-registered-business")
    public ResponseEntity<Integer> totalRegisteredBusiness() {
        return ResponseEntity.ok(dashboardService.getTotalRegisteredBusiness());
    }

    @GetMapping("/total-mdas")
    public ResponseEntity<Integer> totalMdas() {
        return ResponseEntity.ok(dashboardService.getTotalMdas());
    }

    @GetMapping("/pending-cofo")
    public ResponseEntity<Integer> totalPendingCofo() {
        return ResponseEntity.ok(dashboardService.getPendingCofO());
    }

    @GetMapping("/pending-statutory")
    public ResponseEntity<Integer> totalPendingStatutory() {
        return ResponseEntity.ok(dashboardService.getPendingStatutory());
    }

    @GetMapping("/approved-statutory")
    public ResponseEntity<Integer> totalApprovedStatutory() {
        return ResponseEntity.ok(dashboardService.getApprovedStatutory());
    }

    @GetMapping("/pending-ground-rent")
    public ResponseEntity<Integer> totalPendingGroundRent() {
        return ResponseEntity.ok(dashboardService.getPendingGroundRent());
    }

    @GetMapping("/approved-ground-rent")
    public ResponseEntity<Integer> totalApprovedGroundRent() {
        return ResponseEntity.ok(dashboardService.getApprovedGroundRent());
    }


    @GetMapping("/pending-business-registeration")
    public ResponseEntity<Integer> totalPendingBusinessRegistration() {
        return ResponseEntity.ok(dashboardService.getPendingBusinessRegisteration());
    }

    }
