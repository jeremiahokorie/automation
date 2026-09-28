package com.automation.core.reporting.provider.impl;

import com.automation.core.wardactivity.model.ActivityReport;
import com.automation.core.wardactivity.repository.ActivityReportRepository;
import com.automation.core.wardactivity.enums.ActivityStatus;
import com.automation.core.reporting.enums.ExportFormat;
import com.automation.core.reporting.enums.ReportSource;
import com.automation.core.reporting.dto.request.ReportRequest;
import org.springframework.data.domain.PageRequest;
import com.automation.core.reporting.model.ReportResponse;
import com.automation.core.reporting.provider.ReportProvider;
import com.automation.util.ReportUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WardActivityReportProvider implements ReportProvider {
    private final ActivityReportRepository activityReportRepository;

    @Override
    public ReportResponse generateReport(ReportRequest request) {
        // Map string status to ActivityStatus enum
        ActivityStatus status = null;
        if (request.getStatus() != null) {
            try {
                status = ActivityStatus.valueOf(request.getStatus().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Handle invalid status
            }
        }

        // We use the searchAll method from the repository which already supports filtering
        // Since searchAll returns a Page, we fetch the first page with a large size for reporting
        List<ActivityReport> data = activityReportRepository.searchAll(
                null, // wardId - we want all wards if not specified
                status,
                null, // category - not requested in filter
                request.getStartDate(),
                request.getEndDate(),
                PageRequest.of(0, 10000)
        ).getContent();

        byte[] content;
        String contentType;
        String fileName;

        switch (request.getFormat() != null ? request.getFormat() : ExportFormat.JSON) {
            case PDF -> {
                content = ReportUtil.generatePdfReportFromActivity(data, "Ward Activity Report");
                contentType = "application/pdf";
                fileName = "ward_activity_report.pdf";
            }
            case CSV -> {
                content = ReportUtil.generateGenericCsvReport(data,
                    new String[]{"S/N", "Title", "Date", "Status"},
                    a -> new String[]{a.getTitle(), a.getDate().toString(), String.valueOf(a.getStatus())});
                contentType = "text/csv";
                fileName = "ward_activity_report.csv";
            }
            case JSON -> {
                content = ReportUtil.generateJsonReport(data);
                contentType = "application/json";
                fileName = "ward_activity_report.json";
            }
            default -> throw new IllegalArgumentException("Unsupported format: " + request.getFormat());
        }

        return ReportResponse.builder()
                .fileName(fileName)
                .contentType(contentType)
                .content(content)
                .size(content.length)
                .generatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public ReportSource getSource() {
        return ReportSource.WARD_ACTIVITY;
    }


}
