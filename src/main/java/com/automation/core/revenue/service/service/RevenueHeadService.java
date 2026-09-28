package com.automation.core.revenue.service.service;

import com.automation.core.revenue.dto.request.MdaRevenueHeadMappingRequest;
import com.automation.core.revenue.dto.request.RevenueHeadRequest;
import com.automation.core.revenue.dto.response.RevenueHeadResponse;

import java.util.List;

public interface RevenueHeadService {
    RevenueHeadResponse createRevenueHead(RevenueHeadRequest request);
    List<RevenueHeadResponse> getAllRevenueHeads();
    void mapMdaToRevenueHead(MdaRevenueHeadMappingRequest mappingRequest);
}
