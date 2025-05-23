package com.automation.core.basepa.dto.request;

import com.automation.util.enums.PermitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermitRenewRequest {
    private String operationalLicenseNumber;
    private String email;
    private PermitType permitType;
    private String comment;

}
