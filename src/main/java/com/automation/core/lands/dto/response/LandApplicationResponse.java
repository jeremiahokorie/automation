package com.automation.core.lands.dto.response;

import com.automation.util.enums.GlobalStatus;
import com.automation.util.enums.LandApplicationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandApplicationResponse {
    private String applicantName;
    private String email;
    private LandApplicationType applicationType;
    private GlobalStatus status;
    private LocalDateTime applicationDate;
    private LocalDateTime approvalDate;
    private String documents;
    private String certificateUrl;
}
