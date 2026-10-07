package com.automation.core.health.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthSummaryResponse {
    private long totalRegistered;
    private long pending;
    private long approved;
    private long rejected;
}
