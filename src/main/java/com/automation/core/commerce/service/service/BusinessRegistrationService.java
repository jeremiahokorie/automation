package com.automation.core.commerce.service.service;

import com.automation.core.commerce.dto.request.BusinessRegistrationRequest;
import com.automation.core.commerce.dto.request.BusinessRenewalRequest;
import com.automation.core.commerce.dto.response.BusinessRegistrationResponse;
import com.automation.core.commerce.dto.response.BusinessRenewalResponse;
import java.util.List;

public interface BusinessRegistrationService {
    BusinessRegistrationResponse register(BusinessRegistrationRequest businessRegistrationRequest);

    List<BusinessRegistrationResponse> getRegisteredBusiness();

    BusinessRegistrationResponse verifyBusiness(String businessNumber);

    BusinessRenewalResponse renewBusiness(BusinessRenewalRequest businessRenewalRequest);

    BusinessRenewalResponse approveRequest(String businessNumber);

}
