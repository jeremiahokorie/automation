package com.automation.core.commerce.service.serviceImpl;

import com.automation.core.commerce.dto.request.BusinessTypeRequest;
import com.automation.core.commerce.dto.response.BusinessTypeResponse;
import com.automation.core.commerce.model.BusinessType;
import com.automation.core.commerce.repository.BusinessTypeRepository;
import com.automation.core.commerce.service.service.BusinessTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class  BusinessTypeServiceImpl implements BusinessTypeService {
    private final BusinessTypeRepository businessTypeRepository;

    @Override
    public BusinessTypeResponse createBusinessType(BusinessTypeRequest businessTypeRequest) {
        BusinessType businessType = businessTypeRepository.findByname(businessTypeRequest.getName());
        if (businessType == null) {
            businessType = new BusinessType();
            businessType.setName(businessTypeRequest.getName());
            businessType.setName(businessTypeRequest.getName());
            businessTypeRepository.save(businessType);
        }
        return BusinessTypeResponse.builder()
                .name(businessTypeRequest.getName())
                .description(businessTypeRequest.getDescription()).build();
    }

    @Override
    public List<BusinessTypeResponse> getAllBusiness() {
        List<BusinessType> businessTypes = businessTypeRepository.findAll();
        return businessTypes.stream().map(businessType -> BusinessTypeResponse.builder()
                .name(businessType.getName())
                .description(businessType.getDescription()).build()
        ).collect(Collectors.toList());
    }
}
