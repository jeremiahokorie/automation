package com.automation.core.basepa.service.EnvironmentServiceImpl;

import com.automation.core.basepa.dto.request.ApprovalRequest;
import com.automation.core.basepa.dto.request.EnvironmentRequest;
import com.automation.core.basepa.dto.request.PermitRenewRequest;
import com.automation.core.basepa.dto.response.ApprovalResponse;
import com.automation.core.basepa.dto.response.EnvironmentResponse;
import com.automation.core.basepa.dto.response.EnvironmentSummaryResponse;
import com.automation.core.basepa.model.EnvironmentApplication;
import com.automation.core.basepa.repository.EnvironmentRepository;
import com.automation.core.basepa.service.EnvironmentService.EnvironmentService;
import com.automation.core.commerce.dto.request.ApprovalandRejectRequest;
import com.automation.core.commerce.dto.request.BusinessRenewalRequest;
import com.automation.core.commerce.dto.response.ApprovalandRejectResponse;
import com.automation.core.commerce.dto.response.BusinessRenewalResponse;
import com.automation.core.commerce.dto.response.BusinessSummaryResponse;
import com.automation.core.commerce.model.BusinessRegistration;
import com.automation.core.global.exception.CustomException;
import com.automation.core.global.exception.Exception;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.inspection.dto.request.InspectionRequest;
import com.automation.core.inspection.model.Inspection;
import com.automation.core.inspection.repository.InspectionRepository;
import com.automation.core.inspection.service.InspectionService.InspectionService;
import com.automation.core.payment.dto.request.PaymentRequest;
import com.automation.core.payment.service.PaymentService;
import com.automation.util.enums.PermitType;
import com.automation.util.enums.SourceOfWaste;
import com.automation.util.enums.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class EnvironmentServiceImpl implements EnvironmentService {
    private final EnvironmentRepository environmentRepository;
    private final PaymentService paymentService;
    private final InspectionRepository inspectionRepository;
    private final Environment environment;

    @Override
    public EnvironmentResponse apply(EnvironmentRequest environmentRequest) {
        EnvironmentApplication appyPermit = environmentRepository.findByemail(environmentRequest.getEmail());

        // Step 2: Build payment request
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(15000)
                .bearer(1)
                .callbackUrl("https://bauchi-mda.netlify.app/")
                .channels(List.of("card", "bank"))
                .customerFirstName(environmentRequest.getApplicantName())
                .customerLastName(environmentRequest.getApplicantName())
                .customerPhoneNumber(environmentRequest.getPhone())
                .email(environmentRequest.getEmail())
                .build();

        // Step 3: Call the payment gateway
        ResponseEntity<String> paymentResponse = paymentService.initializePayment(paymentRequest);
        if (paymentResponse.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Unable to initiate payment");
        }

        // Step 4: Parse the response JSON
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(paymentResponse.getBody());
            int status = root.path("status").asInt();
            if (status != 200) {
                throw new Exception("Payment failed to initialize");
            }
            String authorizationUrl = root.path("data").path("authorizationUrl").asText();
            log.info("Authorization URL: {}", authorizationUrl);

        if (appyPermit == null) {
            appyPermit = new EnvironmentApplication();
            appyPermit.setEmail(environmentRequest.getEmail());
            appyPermit.setAddress(environmentRequest.getAddress());
            appyPermit.setApplicationDate(LocalDate.now());
            appyPermit.setContactPerson(environmentRequest.getContactPerson());
            appyPermit.setApplicantName(environmentRequest.getApplicantName());
            appyPermit.setFacilityAddress(environmentRequest.getFacilityAddress());
            appyPermit.setFacilityName(environmentRequest.getFacilityName());
            appyPermit.setStatus(Status.PENDING);
            appyPermit.setPermitType(environmentRequest.getPermitType());
            appyPermit.setWasteDescription(environmentRequest.getWasteDescription());
            appyPermit.setPhone(environmentRequest.getPhone());
            appyPermit.setAuthorizationUrl(authorizationUrl);
            appyPermit.setIndustryType(environmentRequest.getIndustryType());
            appyPermit.setWasteQuantity(environmentRequest.getWasteQuantity());
            appyPermit.setOperationalLicenseNumber(environmentRequest.getOperationalLicenseNumber());
            appyPermit.setHasEnvironmentalAudit(true);
            appyPermit.setIsPayed(true);
            appyPermit.setDisposalFrequency(environmentRequest.getDisposalFrequency());
            appyPermit.setDisposalMethod(environmentRequest.getDisposalMethod());

            appyPermit.setWasteSource(SourceOfWaste.HOUSEHOLD);
            environmentRepository.save(appyPermit);

            Inspection inspection = new Inspection();
            inspection.setRequestId(UUID.randomUUID());
            inspection.setSourceService("APPLICATION PERMIT");
            inspection.setApplicantName(environmentRequest.getApplicantName());
            inspection.setApplicationType(environmentRequest.getIndustryType());
            inspectionRepository.save(inspection);
        }
        else {
            throw new ResourceNotFoundException("Resource Not Found");
        }

        return EnvironmentResponse.builder()
                .email(environmentRequest.getEmail())
                .address(environmentRequest.getAddress())
                .applicantName(environmentRequest.getApplicantName())
                .disposalLocation(environmentRequest.getDisposalLocation())
                .disposalMethod(environmentRequest.getDisposalMethod())
                .facilityName(environmentRequest.getFacilityName())
                .permitType(environmentRequest.getPermitType())
                .wasteSource(environmentRequest.getWasteSource())
                .applicationDate(environmentRequest.getApplicationDate())
                .status(environmentRequest.getStatus())
                .wasteQuantity(environmentRequest.getWasteQuantity())
                .disposalFrequency(environmentRequest.getDisposalFrequency())
                .wasteDescription(environmentRequest.getWasteDescription())
                .phone(environmentRequest.getPhone())
                .industryType(environmentRequest.getIndustryType())
                .operationalLicenseNumber(environmentRequest.getOperationalLicenseNumber())
                .contactPerson(environmentRequest.getContactPerson())
                .facilityAddress(environmentRequest.getFacilityAddress())
                .authorizationUrl(authorizationUrl)
                .hasEnvironmentalAudit(environmentRequest.getHasEnvironmentalAudit())
                .build();
        } catch (IOException e) {
            throw new Exception("Payment gateway response parsing error");
        }
    }

    @Override
    public List<EnvironmentResponse> getAll() {
        List<EnvironmentApplication> appyPermit = environmentRepository.findAllByOrderByCreatedAtDesc();
        return appyPermit.stream().map(permit -> EnvironmentResponse.builder()
                .id(permit.getId())
                .phone(permit.getPhone())
                .applicationDate(permit.getApplicationDate())
                .status(permit.getStatus())
                .facilityName(permit.getFacilityName())
                .disposalLocation(permit.getDisposalLocation())
                .facilityAddress(permit.getFacilityAddress())
                .industryType(permit.getIndustryType())
                .applicantName(permit.getApplicantName())
                .contactPerson(permit.getContactPerson())
                .address(permit.getAddress())
                .authorizationUrl(permit.getAuthorizationUrl())
                .permitType(permit.getPermitType())
                .wasteQuantity(permit.getWasteQuantity())
                .disposalFrequency(permit.getDisposalFrequency())
                .disposalMethod(permit.getDisposalMethod())
                .disposalLocation(permit.getDisposalLocation())
                .hasEnvironmentalAudit(permit.getHasEnvironmentalAudit())
                .operationalLicenseNumber(permit.getOperationalLicenseNumber())
                .email(permit.getEmail()).build()
        ).collect(Collectors.toList());
    }


    @Override
    public ApprovalResponse approveRequest(Long id, ApprovalRequest commentRequest) {
        EnvironmentApplication permit = environmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));

        permit.setStatus(Status.APPROVED);
        permit.setComment(commentRequest.getComment());
        permit.setApprovalDate(LocalDate.now());
        environmentRepository.save(permit);
        return ApprovalResponse.builder()
                .comment(permit.getComment())
                .build();
    }


    @Override
    public ApprovalResponse rejectRequest(Long id, ApprovalRequest commentRequest) {
        EnvironmentApplication rejectPermit = environmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));

        rejectPermit.setStatus(Status.REJECTED);
        rejectPermit.setComment(commentRequest.getComment());
        rejectPermit.setRejectionDate(LocalDate.now());
        environmentRepository.save(rejectPermit);

        return ApprovalResponse.builder()
                .comment(rejectPermit.getComment())
                .id(rejectPermit.getId())
                .build();
    }



    @Override
    public EnvironmentResponse renewPermit(PermitRenewRequest permitRenewRequest) {
        EnvironmentApplication renew = environmentRepository.findByoperationalLicenseNumber(permitRenewRequest.getOperationalLicenseNumber()).orElseThrow(()-> new Exception("Permit with Operational Id not found"));
        renew.setStatus(Status.PENDING);

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(15000)
                .bearer(1)
                .callbackUrl("https://example.com/")
                .channels(List.of("card", "bank"))
                .customerFirstName("jerry")
                .customerLastName("imo")
                .customerPhoneNumber(permitRenewRequest.getOperationalLicenseNumber())
                .email(permitRenewRequest.getEmail())
                .build();

        // Step 3: Call the payment gateway
        ResponseEntity<String> paymentResponse = paymentService.initializePayment(paymentRequest);
        if (paymentResponse.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Unable to initiate payment");
        }

        // Step 4: Parse the response JSON
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(paymentResponse.getBody());
            int status = root.path("status").asInt();
            if (status != 200) {
                throw new Exception("Payment failed to initialize");
            }

            String authorizationUrl = root.path("data").path("authorizationUrl").asText();

        environmentRepository.save(renew);


        return EnvironmentResponse.builder()
                .id(renew.getId())
                .disposalLocation(renew.getDisposalLocation())
                .disposalMethod(renew.getDisposalMethod())
                .status(renew.getStatus())
                .applicantName(renew.getApplicantName())
                .email(renew.getEmail())
                .industryType(renew.getIndustryType())
                .facilityName(renew.getFacilityName())
                .phone(renew.getPhone())
                .authorizationUrl(authorizationUrl)
                .permitType(renew.getPermitType())
                .operationalLicenseNumber(renew.getOperationalLicenseNumber()).build();
        } catch (IOException e) {
            throw new Exception("Payment gateway response parsing error");
        }

    }

    @Override
    public EnvironmentSummaryResponse getEnvironmentSummary() {
        EnvironmentSummaryResponse environment = new EnvironmentSummaryResponse();
        environment.setTotalApproved((int) environmentRepository.countByStatus(Status.APPROVED));
        environment.setTotalRejected((int) environmentRepository.countByStatus(Status.REJECTED));
        environment.setTotalPending((int) environmentRepository.countByStatus(Status.PENDING));
        environment.setTotalReviewed((int) environmentRepository.countByStatus(Status.REVIEWED));
        environment.setTotalRegisteredEnvironment(Math.toIntExact(environmentRepository.count()));
        return environment;
    }
}
