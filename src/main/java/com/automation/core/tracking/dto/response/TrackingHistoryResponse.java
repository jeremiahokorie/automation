package com.automation.core.tracking.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import com.automation.core.tracking.enums.ApplicationStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingHistoryResponse {
    private ApplicationStatus status;
    private String stage;
    private String changedBy;
    private String comment;
    private LocalDateTime changedAt;
}
