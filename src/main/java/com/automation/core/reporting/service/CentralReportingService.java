package com.automation.core.reporting.service;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.reporting.enums.ExportFormat;
import com.automation.core.reporting.enums.ReportSource;
import com.automation.core.reporting.model.ReportResponse;
import com.automation.core.reporting.dto.request.ReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CentralReportingService {
    private final ReportingDispatcher reportingDispatcher;

    public AppResponse<ReportResponse> spoolReport(ReportRequest request) {
        ReportResponse report = reportingDispatcher.dispatch(request);
        return AppResponse.<ReportResponse>builder()
                .message("Report generated successfully")
                .status(HttpStatus.OK.value())
                .data(report)
                .build();
    }
}
