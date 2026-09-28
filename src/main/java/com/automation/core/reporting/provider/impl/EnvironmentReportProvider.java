package com.automation.core.reporting.provider.impl;

import com.automation.core.basepa.model.EnvironmentApplication;
import com.automation.core.basepa.repository.EnvironmentRepository;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.reporting.dto.request.ReportRequest;
import com.automation.core.reporting.enums.ExportFormat;
import com.automation.core.reporting.enums.ReportSource;
import com.automation.core.reporting.model.ReportResponse;
import com.automation.core.reporting.provider.ReportProvider;
import com.automation.util.ReportUtil;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnvironmentReportProvider implements ReportProvider {
    private final EnvironmentRepository environmentRepository;
    private final UserRepository userRepository;

    @Override
    public ReportResponse generateReport(ReportRequest request) {
        List<EnvironmentApplication> data;

        LocalDateTime start = request.getStartDate() != null ? request.getStartDate().atStartOfDay() : LocalDateTime.MIN;
        LocalDateTime end = request.getEndDate() != null ? request.getEndDate().atTime(23, 59, 59) : LocalDateTime.MAX;

        User user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId()).orElse(null);
        }

        Status status = null;
        if (request.getStatus() != null) {
            try {
                status = Status.valueOf(request.getStatus().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Log error or handle invalid status
            }
        }

        if (user != null && status != null) {
            data = environmentRepository.findByCreatedByAndStatusAndCreatedAtBetween(user, status, start, end);
        } else if (user != null) {
            data = environmentRepository.findByCreatedByAndCreatedAtBetween(user, start, end);
        } else if (status != null) {
            data = environmentRepository.findByStatusAndCreatedAtBetween(status, start, end);
        } else {
            data = environmentRepository.findByCreatedAtBetween(start, end);
        }

        byte[] content;
        String contentType;
        String fileName;

        switch (request.getFormat() != null ? request.getFormat() : ExportFormat.JSON) {
            case PDF -> {
                content = ReportUtil.generateGenericPdfReport(
                    "Environment Permit Report",
                    "Environmental Protection Agency Report",
                    data,
                    (e) -> new String[]{ "S/N", "Applicant Name", "Facility", "Status" },
                    (e, i) -> new String[]{ String.valueOf(i), e.getApplicantName(), e.getFacilityName(), String.valueOf(e.getStatus()) }
                );
                contentType = "application/pdf";
                fileName = "environment_report.pdf";
            }
            case CSV -> {
                content = ReportUtil.generateGenericCsvReport(data,
                    new String[]{"S/N", "Applicant Name", "Facility", "Status"},
                    e -> new String[]{e.getApplicantName(), e.getFacilityName(), String.valueOf(e.getStatus())});
                contentType = "text/csv";
                fileName = "environment_report.csv";
            }
            case JSON -> {
                content = ReportUtil.generateJsonReport(data);
                contentType = "application/json";
                fileName = "environment_report.json";
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
        return ReportSource.ENVIRONMENT;
    }
}
