package com.automation.core.tracking.dto.response;

import lombok.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingSummaryResponse {
    private long total;
    private Map<String, Long> statusCounts;
    private long actionRequiredCount;
}
