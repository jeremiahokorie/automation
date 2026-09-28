package com.automation.core.global.verification.service;

import com.automation.core.abiaid.service.AbiaStateIdentificationService.AbiaStateIdentificationService;
import com.automation.core.abiaid.dto.response.AbiaStateIdentificationResponse;
import com.automation.core.commerce.service.service.BusinessRegistrationService;
import com.automation.core.commerce.dto.response.BusinessRegistrationResponse;
import com.automation.core.global.verification.dto.VerificationResponse;
import com.automation.core.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GlobalVerificationServiceImpl implements GlobalVerificationService {

    private final AbiaStateIdentificationService abiaIdService;
    private final BusinessRegistrationService businessService;
    // Other domain services will be added here as their verify methods are implemented

    @Override
    public VerificationResponse verifyDocument(String documentNumber) {
        // 1. Try ABSIN (Abia ID)
        try {
            AbiaStateIdentificationResponse idResponse = abiaIdService.verifyByAbiaIdNumber(documentNumber);
            return buildResponse(true, "ABSIN", idResponse.getFirstName() + " " + idResponse.getLastName(),
                                idResponse.getStatus().toString(), idResponse);
        } catch (ResourceNotFoundException e) {
            // Continue to next provider
        }

        // 2. Try Business Registration
        try {
            BusinessRegistrationResponse bizResponse = businessService.verifyBusiness(documentNumber);
            return buildResponse(true, "BUSINESS", bizResponse.getBusinessName(),
                                bizResponse.getStatus().toString(), bizResponse);
        } catch (ResourceNotFoundException e) {
            // Continue to next provider
        }

        // If no provider found a match
        return VerificationResponse.builder()
                .verified(false)
                .message("Document not found or invalid across all government records")
                .build();
    }

    private VerificationResponse buildResponse(boolean verified, String type, String holder, String status, Object details) {
        Map<String, Object> detailsMap = new HashMap<>();
        detailsMap.put("data", details);

        return VerificationResponse.builder()
                .verified(verified)
                .documentType(type)
                .holderName(holder)
                .status(status)
                .details(detailsMap)
                .message("Document verified successfully")
                .build();
    }
}
