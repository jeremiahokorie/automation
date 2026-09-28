package com.automation.core.health.controller;

import com.automation.core.health.dto.request.HealthFacilityRegistrationRequest;
import com.automation.core.health.dto.request.ResearchEthicalApprovalRequest;
import com.automation.core.health.dto.response.HealthRegistrationResponse;
import com.automation.core.health.service.HealthService;
import com.automation.core.global.model.User;
import com.automation.util.enums.Status;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {

    private final HealthService healthService;

    @PostMapping("/register-facility")
    @ApiOperation("Register a new Health Facility")
    public ResponseEntity<HealthRegistrationResponse> registerFacility(
            @RequestBody HealthFacilityRegistrationRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(healthService.registerFacility(request, user));
    }

    @PostMapping("/register-research")
    @ApiOperation("Apply for Research Ethical Approval")
    public ResponseEntity<HealthRegistrationResponse> registerResearch(
            @RequestBody ResearchEthicalApprovalRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(healthService.registerResearch(request, user));
    }

    @PutMapping("/review/{id}")
    @ApiOperation("Update application status (Admin only)")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam Status status,
            @RequestParam String officer,
            @RequestParam String comment) {
        healthService.updateFacilityStatus(id, status, officer, comment);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/approve/{id}")
    @ApiOperation("Final approval and licence issuance (Admin only)")
    public ResponseEntity<Void> approve(
            @PathVariable Long id,
            @RequestParam String permitUrl) {
        healthService.approveFacility(id, permitUrl);
        return ResponseEntity.ok().build();
    }
}
