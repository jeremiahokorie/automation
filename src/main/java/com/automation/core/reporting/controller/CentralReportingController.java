package com.automation.core.reporting.controller;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.reporting.dto.request.ReportRequest;
import com.automation.core.reporting.model.ReportResponse;
import com.automation.core.reporting.service.CentralReportingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reporting")
@RequiredArgsConstructor
public class CentralReportingController {
    private final CentralReportingService reportingService;

    @Operation(summary = "Spool reports from integrated services",
               description = "Allows admins to generate reports from various sources in different formats.")
    @GetMapping("/spool")
    public ResponseEntity<AppResponse<ReportResponse>> spoolReport(ReportRequest request) {
        AppResponse<ReportResponse> response = reportingService.spoolReport(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
