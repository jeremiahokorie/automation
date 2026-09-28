package com.automation.core.reporting.provider;

import com.automation.core.reporting.enums.ExportFormat;
import com.automation.core.reporting.enums.ReportSource;
import com.automation.core.reporting.model.ReportResponse;
import com.automation.core.reporting.dto.request.ReportRequest;
import java.time.LocalDate;

public interface ReportProvider {
    ReportResponse generateReport(ReportRequest request);
    ReportSource getSource();
}
