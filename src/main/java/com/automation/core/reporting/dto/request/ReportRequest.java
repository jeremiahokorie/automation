package com.automation.core.reporting.dto.request;

import com.automation.core.reporting.enums.ExportFormat;
import com.automation.core.reporting.enums.ReportSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {
    private ReportSource source;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String mdaType;
    private Long userId;
    private ExportFormat format;
}
