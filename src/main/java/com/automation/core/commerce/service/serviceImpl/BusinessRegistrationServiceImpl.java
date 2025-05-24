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
import com.automation.core.payment.dto.request.PaymentRequest;
import com.automation.core.payment.service.PaymentService;
import com.automation.util.enums.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BusinessRegistrationServiceImpl implements BusinessRegistrationService {
    private final BusinessRepository businessRepository;
    private final BusinessTypeRepository businessTypeRepository;
    private final PaymentService paymentService;

    @Override
    public BusinessRegistrationResponse register(BusinessRegistrationRequest businessRegistrationRequest) {
        BusinessRegistration businessRegistration = businessRepository.findBybusinessNumber(businessRegistrationRequest.getBusinessNumber());

        if (businessRegistration != null) {
            throw new Exception("Business already exists");
        }

        // Step 2: Build payment request
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(15000)
                .bearer(1)
                .callbackUrl("https://example.com/")
                .channels(List.of("card", "bank"))
                .customerFirstName(businessRegistrationRequest.getOwnerName())
                .customerLastName(businessRegistrationRequest.getOwnerName())
                .customerPhoneNumber(businessRegistrationRequest.getPhone())
                .email(businessRegistrationRequest.getEmail())
                .build();

        // Step 3: Call the payment gateway
        ResponseEntity<String> paymentResponse = paymentService.initializePayment(paymentRequest);
        if (paymentResponse.getStatusCode() != HttpStatus.OK) {
            throw new CustomException("Unable to initiate payment");
        }

        // Step 4: Parse the response JSON
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(paymentResponse.getBody());
            int status = root.path("status").asInt();
            if (status != 200) {
                throw new CustomException("Payment failed to initialize");
            }

            String authorizationUrl = root.path("data").path("authorizationUrl").asText();


            if (businessRegistration == null) {
            businessRegistration = new BusinessRegistration();
            businessRegistration.setBusinessNumber(businessRegistrationRequest.getBusinessNumber());
            businessRegistration.setBusinessName(businessRegistrationRequest.getBusinessName());
            businessRegistration.setEmail(businessRegistrationRequest.getEmail());
            businessRegistration.setPhone(businessRegistrationRequest.getPhone());
            businessRegistration.setAddress(businessRegistrationRequest.getAddress());
            businessRegistration.setStatus(Status.PENDING);
            businessRegistration.setComment(businessRegistrationRequest.getComment());
            businessRegistration.setOwnerName(businessRegistrationRequest.getOwnerName());
            businessRegistration.setDateRegistered(LocalDate.now());
           // businessRegistration.setBusinessType(businessType);
            businessRepository.save(businessRegistration);
        }else {
            throw new CustomException("Business already exists");
        }

        return BusinessRegistrationResponse.builder()
                .comment(businessRegistrationRequest.getComment())
                .status(businessRegistrationRequest.getStatus())
                .businessName(businessRegistrationRequest.getBusinessName())
                .businessNumber(businessRegistrationRequest.getBusinessNumber())
                .dateRegistered(LocalDate.now())
                .ownerName(businessRegistrationRequest.getOwnerName())
                .phone(businessRegistrationRequest.getPhone())
                .address(businessRegistrationRequest.getAddress())
                .email(businessRegistrationRequest.getEmail())
                .authorizationUrl(authorizationUrl)
                .isRenewal(true)
                .build();
        } catch (IOException e) {
            throw new CustomException("Payment gateway response parsing error");
        }
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
        registration.setStatus(Status.PENDING);
        businessRepository.save(registration);
        return BusinessRenewalResponse.builder()
                .id(registration.getId())
                .businessNumber(registration.getBusinessNumber())
                .renewalDate(registration.getRenewalDate())
                .status(registration.getStatus())
                .comment(registration.getComment())
                .businessName(registration.getBusinessName()).build();
    }



    @Override
    public ApprovalandRejectResponse approveRequest(String businessNumber, ApprovalandRejectRequest comment) {
        BusinessRegistration registration = businessRepository.findBybusinessNumber(businessNumber);
        if (registration == null) {
            throw new Exception("Business not found or not yet due for renewal.");
        }
        registration.setStatus(Status.APPROVED);
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

        registration.setStatus(Status.REJECTED);
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

