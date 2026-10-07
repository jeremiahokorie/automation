package com.automation.core.health.controller;

import com.automation.core.health.dto.request.HealthFacilityRegistrationRequest;
import com.automation.core.health.dto.request.ResearchEthicalApprovalRequest;
import com.automation.core.health.dto.response.HealthRegistrationResponse;
import com.automation.core.health.dto.response.HealthSummaryResponse;
import com.automation.core.health.service.HealthService;
import com.automation.core.global.model.User;
import com.automation.util.enums.Status;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
            @Valid @RequestBody HealthFacilityRegistrationRequest request) {
        return ResponseEntity.ok(healthService.registerFacility(request));
    }

    @GetMapping("/facilities")
    @ApiOperation("Get paginated health facilities")
    public ResponseEntity<Page<HealthRegistrationResponse>> getFacilities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) Status status) {
        return ResponseEntity.ok(healthService.getFacilities(page, size, sortBy, sortDir, status));
    }

    @PostMapping("/register-research")
    @ApiOperation("Apply for Research Ethical Approval")
    public ResponseEntity<HealthRegistrationResponse> registerResearch(
            @Valid @RequestBody ResearchEthicalApprovalRequest request) {
        return ResponseEntity.ok(healthService.registerResearch(request));
    }

    @GetMapping("/research")
    @ApiOperation("Get paginated research applications")
    public ResponseEntity<Page<HealthRegistrationResponse>> getResearchApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) Status status) {
        return ResponseEntity.ok(healthService.getResearchApplications(page, size, sortBy, sortDir, status));
    }

    @GetMapping("/summary")
    @ApiOperation("Get health registration summary")
    public ResponseEntity<HealthSummaryResponse> getSummary() {
        return ResponseEntity.ok(healthService.getHealthSummary());
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
