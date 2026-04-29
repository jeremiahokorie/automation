package com.automation.core.commerce.service.service;

import com.automation.core.commerce.dto.request.ApprovalandRejectRequest;
import com.automation.core.commerce.dto.request.BusinessRegistrationRequest;
import com.automation.core.commerce.dto.request.BusinessRenewalRequest;
import com.automation.core.commerce.dto.response.ApprovalandRejectResponse;
import com.automation.core.commerce.dto.response.BusinessRegistrationResponse;
import com.automation.core.commerce.dto.response.BusinessRenewalResponse;
import com.automation.core.commerce.dto.response.BusinessSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BusinessRegistrationService {
    BusinessRegistrationResponse register(BusinessRegistrationRequest businessRegistrationRequest);

    List<BusinessRegistrationResponse> getRegisteredBusiness();

    BusinessRegistrationResponse verifyBusiness(String businessNumber);

    BusinessRenewalResponse renewBusiness(BusinessRenewalRequest businessRenewalRequest);

    ApprovalandRejectResponse approveRequest(String businessNumber, ApprovalandRejectRequest comment);

    ApprovalandRejectResponse rejectRequest(String businessNumber, ApprovalandRejectRequest comment);

    BusinessSummaryResponse getBusinessSummary();

    Page<BusinessRegistrationResponse> getAllRegisteredBusiness(int page, int size, String sortBy, String sortDir);

    Page<BusinessRegistrationResponse> getPaginatedBusinesses(int offset, int pageSize);
}
