package com.automation.core.commerce.service.serviceImpl;

import com.automation.core.commerce.dto.request.ApprovalandRejectRequest;
import com.automation.core.commerce.dto.request.BusinessRegistrationRequest;
import com.automation.core.commerce.dto.request.BusinessRenewalRequest;
import com.automation.core.commerce.dto.response.ApprovalandRejectResponse;
import com.automation.core.commerce.dto.response.BusinessRegistrationResponse;
import com.automation.core.commerce.dto.response.BusinessRenewalResponse;
import com.automation.core.commerce.model.BusinessRegistration;
import com.automation.core.commerce.model.BusinessType;
import com.automation.core.commerce.repository.BusinessRepository;
import com.automation.core.commerce.repository.BusinessTypeRepository;
import com.automation.core.commerce.service.service.BusinessRegistrationService;
import com.automation.core.global.exception.CustomException;
import com.automation.core.global.exception.Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BusinessRegistrationServiceImpl implements BusinessRegistrationService {
    private final BusinessRepository businessRepository;
    private final BusinessTypeRepository businessTypeRepository;

    @Override
    public BusinessRegistrationResponse register(BusinessRegistrationRequest businessRegistrationRequest) {
        BusinessRegistration businessRegistration = businessRepository.findBybusinessNumber(businessRegistrationRequest.getBusinessNumber());

//        BusinessType businessType = businessTypeRepository.findById(businessRegistrationRequest.getBusinessTypeId())
//                .orElseThrow(() -> new CustomException("Business Type not found"));

        if (businessRegistration == null) {
            businessRegistration = new BusinessRegistration();
            businessRegistration.setBusinessNumber(businessRegistrationRequest.getBusinessNumber());
            businessRegistration.setBusinessName(businessRegistrationRequest.getBusinessName());
            businessRegistration.setEmail(businessRegistrationRequest.getEmail());
            businessRegistration.setPhone(businessRegistrationRequest.getPhone());
            businessRegistration.setAddress(businessRegistrationRequest.getAddress());
            businessRegistration.setStatus("PENDING");
            businessRegistration.setOwnerName(businessRegistrationRequest.getOwnerName());
            businessRegistration.setDateRegistered(LocalDate.now());
           // businessRegistration.setBusinessType(businessType);
            businessRepository.save(businessRegistration);
        }else {
            throw new CustomException("Business already exists");
        }

        return BusinessRegistrationResponse.builder()
                .businessName(businessRegistrationRequest.getBusinessName())
                .businessNumber(businessRegistrationRequest.getBusinessNumber())
                .dateRegistered(LocalDate.now())
                .ownerName(businessRegistrationRequest.getOwnerName())
                .phone(businessRegistrationRequest.getPhone())
                .address(businessRegistrationRequest.getAddress())
                .email(businessRegistrationRequest.getEmail())
               // .businessType(businessRegistration.getBusinessType())
                .isRenewal(true)
                .build();
    }

    @Override
    public List<BusinessRegistrationResponse> getRegisteredBusiness() {
        List<BusinessRegistration> businessRegistrations = businessRepository.findAll();
        return businessRegistrations.stream().map(businessRegistration -> BusinessRegistrationResponse.builder()
                .id(businessRegistration.getId())
                .email(businessRegistration.getEmail())
                .ownerName(businessRegistration.getOwnerName())
                .businessName(businessRegistration.getBusinessName())
                .address(businessRegistration.getAddress())
                .businessNumber(businessRegistration.getBusinessNumber())
                .isRenewal(true)
                .comment(businessRegistration.getComment())
                .status(businessRegistration.getStatus())
                .phone(businessRegistration.getPhone())
                .dateRegistered(businessRegistration.getDateRegistered()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public BusinessRegistrationResponse verifyBusiness(String businessNumber) {
        BusinessRegistration businessRegistration = businessRepository.findBybusinessNumber(businessNumber);
        if (businessRegistration == null) {
            throw new CustomException("Invalid business number");
        }
        return BusinessRegistrationResponse.builder()
                .id(businessRegistration.getId())
                .businessName(businessRegistration.getBusinessName())
                .businessNumber(businessRegistration.getBusinessNumber())
                .address(businessRegistration.getAddress())
                .email(businessRegistration.getEmail())
                .phone(businessRegistration.getPhone())
                .comment(businessRegistration.getComment())
                .ownerName(businessRegistration.getOwnerName())
                .dateRegistered(businessRegistration.getDateRegistered())
                .isRenewal(businessRegistration.isRenewal())
                .build();
    }

    @Override
    public BusinessRenewalResponse renewBusiness(BusinessRenewalRequest businessRenewalRequest) {
        BusinessRegistration registration = businessRepository.findBybusinessNumber(businessRenewalRequest.getBusinessNumber());
        if (registration == null) {
            throw new CustomException("Business not found or not yet due for renewal.");
        }
        registration.setStatus("PENDING");
        businessRepository.save(registration);
        return BusinessRenewalResponse.builder()
                .id(registration.getId())
                .businessNumber(registration.getBusinessNumber())
                .renewalDate(registration.getRenewalDate())
                .status(registration.getStatus())
                .businessName(registration.getBusinessName()).build();
    }



    @Override
    public ApprovalandRejectResponse approveRequest(String businessNumber, ApprovalandRejectRequest comment) {
        BusinessRegistration registration = businessRepository.findBybusinessNumber(businessNumber);
        if (registration == null) {
            throw new Exception("Business not found or not yet due for renewal.");
        }
        registration.setStatus("APPROVED");
        registration.setComment(comment.getComment());
        registration.setRenewalDate(LocalDate.now());
        businessRepository.save(registration);
        return ApprovalandRejectResponse.builder()
                .id(registration.getId())
                .comment(registration.getComment())
                .build();
    }


    @Override
    public ApprovalandRejectResponse rejectRequest(String businessNumber, ApprovalandRejectRequest request) {
        BusinessRegistration registration = businessRepository.findBybusinessNumber(businessNumber);
        if (registration == null) {
            throw new Exception("Business not found or not yet due for renewal.");
        }

        registration.setStatus("REJECT");
        registration.setComment(request.getComment());

        businessRepository.save(registration);

        return ApprovalandRejectResponse.builder()
                .id(registration.getId())
                .comment(registration.getComment())
                .build();
    }


//    @Override
//    public BusinessRenewalResponse approveRequest(String businessNumber) {
//        BusinessRegistration registration = businessRepository.findBybusinessNumber(businessNumber);
//        if (registration == null || !registration.isExpired()) {
//            throw new CustomException("Business not found or not yet due for renewal.");
//        }
//        registration.setStatus("APPROVED");
//        businessRepository.save(registration);
//        return BusinessRenewalResponse.builder()
//                .businessNumber(registration.getBusinessNumber())
//                .renewalDate(registration.getRenewalDate())
//                .status(registration.getStatus())
//                .businessName(registration.getBusinessName())
//                .status(registration.getStatus())
//                .businessName(registration.getBusinessName()).build();
//    }
}

