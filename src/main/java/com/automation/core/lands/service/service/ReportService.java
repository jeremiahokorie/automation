package com.automation.core.lands.service.service;

import com.automation.util.enums.ReportType;

import java.time.LocalDate;

public interface ReportService {
    byte[] generateReport(LocalDate startDate, LocalDate endDate, ReportType reportType);

}
