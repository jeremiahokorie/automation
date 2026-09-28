package com.automation.core.tracking.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import com.automation.core.tracking.enums.ApplicationStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingDetailsResponse {
    private String trackingReference;
    private String serviceName;
    private ApplicationStatus status;
    private String currentStage;
    private LocalDateTime submittedAt;
    private LocalDateTime lastUpdatedAt;
    private boolean actionRequired;
    private String actionMessage;
    private List<TrackingHistoryResponse> history;
}
