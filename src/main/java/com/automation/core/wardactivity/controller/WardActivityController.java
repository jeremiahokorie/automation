package com.automation.core.wardactivity.controller;

import com.automation.core.wardactivity.dto.request.CreateActivityReportRequest;
import com.automation.core.wardactivity.dto.request.CreateWardRequest;
import com.automation.core.wardactivity.dto.request.UpdateActivityReportRequest;
import com.automation.core.wardactivity.dto.response.ActivityReportResponse;
import com.automation.core.wardactivity.dto.response.WardSimpleResponse;
import com.automation.core.wardactivity.enums.ActivityCategory;
import com.automation.core.wardactivity.enums.ActivityStatus;
import com.automation.core.wardactivity.service.WardActivityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping
public class WardActivityController {

    private final WardActivityService wardActivityService;

    public WardActivityController(WardActivityService wardActivityService) {
        this.wardActivityService = wardActivityService;
    }

    @Operation(summary = "List wards (authenticated users)")
    @GetMapping("/wards")
    public Page<WardSimpleResponse> listWards(Pageable pageable, Authentication authentication) {
        return wardActivityService.getWards(pageable, authentication.getName());
    }

    @Operation(summary = "Create ward (REPRESENTATIVE only)")
    @PostMapping("/wards")
    public WardSimpleResponse createWard(@Valid @RequestBody CreateWardRequest request, Authentication authentication) {
        return wardActivityService.createWard(request, authentication.getName());
    }

    @Operation(summary = "Get reports (REPRESENTATIVE all; WARD_LEADER own ward)")
    @GetMapping("/reports")
    public Page<ActivityReportResponse> getReports(
            @RequestParam(required = false) String wardId,
            @RequestParam(required = false) ActivityStatus status,
            @RequestParam(required = false) ActivityCategory category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            Pageable pageable,
            Authentication authentication
    ) {
        return wardActivityService.getReports(authentication.getName(), wardId, status, category, from, to, pageable);
    }

    @Operation(summary = "Create report (WARD_LEADER only)")
    @PostMapping("/reports")
    public ActivityReportResponse createReport(@Valid @RequestBody CreateActivityReportRequest request, Authentication authentication) {
        return wardActivityService.createReport(request, authentication.getName());
    }

    @Operation(summary = "Get report details by id")
    @GetMapping("/reports/{id}")
    public ActivityReportResponse getReportById(@PathVariable Long id, Authentication authentication) {
        return wardActivityService.getReportById(id, authentication.getName());
    }

    @Operation(summary = "Update report")
    @PutMapping("/reports/{id}")
    public ActivityReportResponse updateReport(
            @PathVariable Long id,
            @Valid @RequestBody UpdateActivityReportRequest request,
            Authentication authentication
    ) {
        return wardActivityService.updateReport(id, request, authentication.getName());
    }

    @Operation(summary = "Representative dashboard summary")
    @GetMapping("/ward/summary")
    public Map<String, Object> summary(Authentication authentication) {
        return wardActivityService.getDashboardSummary(authentication.getName());
    }
}
