package com.automation.core.tracking.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import com.automation.core.tracking.enums.ApplicationStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingApplicationResponse {
    private String trackingReference;
    private String serviceName;
    private ApplicationStatus status;
    private String currentStage;
    private boolean actionRequired;
    private LocalDateTime updatedAt;
}
