package com.automation.core.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {

    private int totalApprovedCofO;
    private int totalRejectedCofO;
    private int totalRegisteredBusiness;
    private int totalMdas;
    private int pendingCofO;
    private int pendingStatutory;
    private int approvedStatutory;
    private int pendingGroundRent;
    private int approvedGroundRent;
    private int pendingBusinessRegistration;

    private int totalRegisteredSchools;
    private int pendingSchools;
    private int approvedSchools;
    private int rejectedSchools;

    private int totalRegisteredHealthFacilities;
    private int pendingHealthFacilities;
    private int approvedHealthFacilities;
    private int rejectedHealthFacilities;
}
