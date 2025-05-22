package com.automation.core.basepa.service.EnvironmentServiceImpl;

import com.automation.core.basepa.dto.request.ApprovalRequest;
import com.automation.core.basepa.dto.request.EnvironmentRequest;
import com.automation.core.basepa.dto.response.ApprovalResponse;
import com.automation.core.basepa.dto.response.EnvironmentResponse;
import com.automation.core.basepa.model.EnvironmentApplication;
import com.automation.core.basepa.repository.EnvironmentRepository;
import com.automation.core.basepa.service.EnvironmentService.EnvironmentService;
import com.automation.core.commerce.dto.request.ApprovalandRejectRequest;
import com.automation.core.commerce.dto.response.ApprovalandRejectResponse;
import com.automation.core.commerce.model.BusinessRegistration;
import com.automation.core.global.exception.Exception;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.util.enums.PermitType;
import com.automation.util.enums.SourceOfWaste;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EnvironmentServiceImpl implements EnvironmentService {
    private final EnvironmentRepository environmentRepository;


    @Override
    public EnvironmentResponse apply(EnvironmentRequest environmentRequest) {
        EnvironmentApplication appyPermit = environmentRepository.findByemail(environmentRequest.getEmail());
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
            appyPermit.setIndustryType(environmentRequest.getIndustryType());
            appyPermit.setWasteQuantity(environmentRequest.getWasteQuantity());
            appyPermit.setOperationalLicenseNumber(environmentRequest.getOperationalLicenseNumber());
            appyPermit.setHasEnvironmentalAudit(true);
            appyPermit.setDisposalFrequency(environmentRequest.getDisposalFrequency());
            appyPermit.setDisposalMethod(environmentRequest.getDisposalMethod());

            appyPermit.setWasteSource(SourceOfWaste.Household);
            environmentRepository.save(appyPermit);
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
                .hasEnvironmentalAudit(environmentRequest.getHasEnvironmentalAudit())
                .build();
    }

    @Override
    public List<EnvironmentResponse> getAll() {
        List<EnvironmentApplication> appyPermit = environmentRepository.findAll();
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
}
