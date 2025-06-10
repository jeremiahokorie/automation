package com.automation.core.lands.service.serviceImpl;


import com.automation.core.lands.dto.request.LandApplicationRequest;
import com.automation.core.lands.dto.response.LandApplicationResponse;
import com.automation.core.lands.model.LandApplication;
import com.automation.core.lands.repository.LandApplicationRepository;
import com.automation.core.lands.service.service.LandApplicationService;
import com.automation.util.enums.GlobalStatus;
import com.automation.util.enums.LandApplicationType;
import com.automation.util.enums.ReportType;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LandApplicationServiceImpl implements LandApplicationService {

    private final LandApplicationRepository landApplicationRepository;

    @Override
    public LandApplicationResponse applyForLand(LandApplicationRequest landApplicationRequest) {
        LandApplication landApplication = new LandApplication();
        landApplication.setApplicantName(landApplicationRequest.getApplicantName());
        landApplication.setApplicationType(LandApplicationType.COFO);
        landApplication.setApplicationDate(LocalDateTime.now());
        landApplication.setStatus(Status.PENDING);
        landApplicationRepository.save(landApplication);
        return LandApplicationResponse.builder()
                .applicationDate(LocalDateTime.now())
                .applicationType(LandApplicationType.COFO)
                .status(Status.PENDING)
                .applicantName(landApplicationRequest.getApplicantName()).build();
    }

    @Override
    public List<LandApplicationResponse> getAllApplication() {
        List<LandApplication> landApplications = landApplicationRepository.findAll();
        return landApplications.stream().map(landApplication -> LandApplicationResponse.builder()
                .email(landApplication.getEmail())
                .applicationType(landApplication.getApplicationType())
                .applicationDate(landApplication.getApplicationDate())
                .status(landApplication.getStatus())
                .build()
        ).collect(Collectors.toList());
    }


    @Override
    public List<LandApplicationResponse> getByTypeAndDate(LandApplicationType type, LocalDate start, LocalDate end) {
        List<LandApplication> applications = landApplicationRepository.findByApplicationTypeAndApplicationDateBetween(
                type, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        return applications.stream().map(landApplication -> LandApplicationResponse.builder()
                .id(landApplication.getId())
                .applicantEmail(landApplication.getApplicantEmail())
                .approvalDate(landApplication.getApprovalDate())
                .administrativeCharges(landApplication.getAdministrativeCharges())
                .applicationDate(landApplication.getApplicationDate())
                .certificateUrl(landApplication.getCertificateUrl())
                .applicationType(landApplication.getApplicationType())
                .status(landApplication.getStatus())
                .districtHeadLetter(landApplication.getDistrictHeadLetter())
                .documents(landApplication.getDocuments())
                .processingFees(landApplication.getProcessingFees())
                .declarationOfAge(landApplication.getDeclarationOfAge())
                .localGovernmentConfirmationLetter(landApplication.getLocalGovernmentConfirmationLetter())
                .taxClearances(landApplication.getTaxClearances())
                .certificateUrl(landApplication.getCertificateUrl())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<LandApplicationResponse> getFilteredReport(LandApplicationType type, String applicantName, ReportType reportType,
                                                           LocalDate startDate, LocalDate endDate) {
        List<LandApplication> applications = landApplicationRepository.findByApplicationTypeAndApplicationDateBetween(
                type, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        if (applicantName != null && !applicantName.isEmpty()) {
            applications = applications.stream()
                    .filter(app -> app.getApplicantName().equalsIgnoreCase(applicantName))
                    .toList();
        }

        switch (reportType) {
            case DAILY:
                // No grouping needed
                return applications.stream().map(this::mapToResponse).toList();

            case MONTHLY:
                return applications.stream()
                        .collect(Collectors.groupingBy(app -> YearMonth.from(app.getApplicationDate())))
                        .entrySet().stream()
                        .flatMap(entry -> entry.getValue().stream().map(this::mapToResponse))
                        .toList();

            case YEARLY:
                return applications.stream()
                        .collect(Collectors.groupingBy(app -> app.getApplicationDate().getYear()))
                        .entrySet().stream()
                        .flatMap(entry -> entry.getValue().stream().map(this::mapToResponse))
                        .toList();
            default:
                throw new IllegalArgumentException("Unsupported Report Type");
        }
        // Optionally group by daily/monthly/yearly
//        return applications.stream().map(landApplication -> LandApplicationResponse.builder()
//                .id(landApplication.getId())
//                .applicantEmail(landApplication.getApplicantEmail())
//                .approvalDate(landApplication.getApprovalDate())
//                .administrativeCharges(landApplication.getAdministrativeCharges())
//                .applicationDate(landApplication.getApplicationDate())
//                .certificateUrl(landApplication.getCertificateUrl())
//                .applicationType(landApplication.getApplicationType())
//                .status(landApplication.getStatus())
//                .districtHeadLetter(landApplication.getDistrictHeadLetter())
//                .documents(landApplication.getDocuments())
//                .processingFees(landApplication.getProcessingFees())
//                .declarationOfAge(landApplication.getDeclarationOfAge())
//                .localGovernmentConfirmationLetter(landApplication.getLocalGovernmentConfirmationLetter())
//                .taxClearances(landApplication.getTaxClearances())
//                .certificateUrl(landApplication.getCertificateUrl())
//                .build()).collect(Collectors.toList());

    }

    private LandApplicationResponse mapToResponse(LandApplication app) {
        return LandApplicationResponse.builder()
                .id(app.getId())
                .applicantEmail(app.getApplicantEmail())
                .approvalDate(app.getApprovalDate())
                .administrativeCharges(app.getAdministrativeCharges())
                .applicationDate(app.getApplicationDate())
                .certificateUrl(app.getCertificateUrl())
                .applicationType(app.getApplicationType())
                .status(app.getStatus())
                .districtHeadLetter(app.getDistrictHeadLetter())
                .documents(app.getDocuments())
                .processingFees(app.getProcessingFees())
                .declarationOfAge(app.getDeclarationOfAge())
                .localGovernmentConfirmationLetter(app.getLocalGovernmentConfirmationLetter())
                .taxClearances(app.getTaxClearances())
                .build();
    }

}
