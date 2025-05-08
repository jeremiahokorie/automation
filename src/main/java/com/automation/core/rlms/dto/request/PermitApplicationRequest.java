package com.automation.core.rlms.dto.request;

import com.automation.util.enums.PermitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermitApplicationRequest {
    private String applicantName;
    private String email;
    private PermitType permitType;
    private String documentUrl;
}
