package com.automation.core.reporting.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ReportResponse {
    private String fileName;
    private String contentType;
    private byte[] content;
    private long size;
    private LocalDateTime generatedAt;
}
