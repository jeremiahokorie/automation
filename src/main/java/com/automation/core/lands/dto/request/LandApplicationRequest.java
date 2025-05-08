package com.automation.core.lands.dto.request;


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
public class LandApplicationRequest {
    private Long id;
    private String applicantName;
    private String email;
    private LandApplicationType applicationType;
    private String status;
    private LocalDateTime applicationDate;
    private LocalDateTime approvalDate;
    private String documents;
    private String certificateUrl;
}
