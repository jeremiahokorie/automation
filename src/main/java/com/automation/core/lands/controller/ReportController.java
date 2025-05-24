package com.automation.core.lands.controller;

import com.automation.core.lands.dto.request.ReportRequest;
import com.automation.core.lands.dto.response.LandApplicationResponse;
import com.automation.core.lands.service.service.CertificateOfOccupancyService;
import com.automation.core.lands.service.service.GroundRentService;
import com.automation.core.lands.service.service.LandApplicationService;
import com.automation.core.lands.service.service.StatutoryAllocationService;
import com.automation.core.lands.service.serviceImpl.ReportDispatcher;
import com.automation.util.enums.LandApplicationType;
import com.automation.util.enums.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/report/lands")
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
public class ReportController {
    private final StatutoryAllocationService statutoryAllocationService;
    private final CertificateOfOccupancyService  certificateOfOccupancyService;
    private final GroundRentService grountRentService;
    private final LandApplicationService landApplicationService;


    private final ReportDispatcher reportDispatcher;

    @PostMapping("/generate-report")
    public ResponseEntity<byte[]> generateReport(@RequestBody ReportRequest request) {
        byte[] file = reportDispatcher.dispatch(
                request.getApplicationType(),
                request.getStartDate(),
                request.getEndDate(),
                request.getReportType()
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }

    @GetMapping("/by-type")
    public List<LandApplicationResponse> getReportByType(
            @RequestParam LandApplicationType applicationType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return landApplicationService.getByTypeAndDate(applicationType, startDate, endDate);
    }

    @GetMapping("/filtered")
    public List<LandApplicationResponse> getAdvancedReport(
            @RequestParam LandApplicationType applicationType,
            @RequestParam(required = false) String applicantName,
            @RequestParam ReportType reportType, // DAILY, MONTHLY, YEARLY
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return landApplicationService.getFilteredReport(applicationType, applicantName, reportType, startDate, endDate);
    }
}


