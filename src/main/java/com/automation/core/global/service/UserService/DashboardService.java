package com.automation.core.global.service.UserService;

import com.automation.core.global.dto.response.AppResponse;

public interface DashboardService {

    Integer getTotalApprovedCofO();

    Integer getTotalRejectedCofO();

    Integer getTotalRegisteredBusiness();

    Integer getTotalMdas();

    Integer getPendingCofO();

    Integer getPendingStatutory();

    Integer getApprovedStatutory();

    Integer getPendingGroundRent();

    Integer getApprovedGroundRent();

    Integer getPendingBusinessRegisteration();
}
