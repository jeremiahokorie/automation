package com.automation.core.commerce.service.service;


import com.automation.core.commerce.dto.request.BusinessTypeRequest;
import com.automation.core.commerce.dto.response.BusinessTypeResponse;

import java.util.List;

public interface BusinessTypeService {
    BusinessTypeResponse createBusinessType(BusinessTypeRequest businessTypeRequest);

    List<BusinessTypeResponse> getAllBusiness();
}
