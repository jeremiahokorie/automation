package com.automation.core.lands.dto.request;

import com.automation.util.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroundRentRequest {
    private String baNo;
    private String landNo;
    private String record;
    private Double rent;
    private String status;
    private String optionalFile;
    private LocalDateTime createdAt = LocalDateTime.now();
}
