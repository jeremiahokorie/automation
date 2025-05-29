package com.automation.core.inspection.dto.request;


import com.automation.util.enums.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionRequest {
    private UUID requestId;
    private String sourceService;
    private String applicantName;
    private String applicationType;
    private Status status;

    private String assignedTo;
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
