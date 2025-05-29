package com.automation.core.inspection.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class StatusUpdateDto {
    private String newStatus; // REVIEWED, VERIFIED, etc.
    private String updatedBy; // name of inspector
    private String notes;     // optional comment
}
