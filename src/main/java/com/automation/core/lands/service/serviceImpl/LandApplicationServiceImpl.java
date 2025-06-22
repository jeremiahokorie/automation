package com.automation.core.lands.service.serviceImpl;


import com.automation.core.basepa.dto.response.EnvironmentSummaryResponse;
import com.automation.core.commerce.dto.response.LandApplicationSummaryResponse;
import com.automation.core.lands.dto.request.CustomaryAllocationRequest;
import com.automation.core.lands.dto.request.LandApplicationRequest;
import com.automation.core.lands.dto.request.StatutoryApplicationRequest;
import com.automation.core.lands.dto.response.CustomaryAllocationResponse;
import com.automation.core.lands.dto.response.LandApplicationResponse;
import com.automation.core.lands.dto.response.StatutoryApplicationResponse;
import com.automation.core.lands.model.CustomaryAllocationApplication;
import com.automation.core.lands.model.LandApplication;
import com.automation.core.lands.model.StatutoryAllocation;
import com.automation.core.lands.model.StatutoryAllocationApplication;
import com.automation.core.lands.repository.CustomaryAllocationRepository;
import com.automation.core.lands.repository.LandApplicationRepository;
import com.automation.core.lands.repository.StatutoryAllocationRepository;
import com.automation.core.lands.repository.StatutoryApplicationRepository;
import com.automation.core.lands.service.service.LandApplicationService;
import com.automation.util.enums.GlobalStatus;
import com.automation.util.enums.LandApplicationType;
import com.automation.util.enums.ReportType;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LandApplicationServiceImpl implements LandApplicationService {

    private final LandApplicationRepository landApplicationRepository;
    private final StatutoryApplicationRepository statutoryApplicationRepository;
    private final CustomaryAllocationRepository customaryAllocationRepository;

    private static final String UPLOAD_DIR_ = "/opt/uploads/customary-allocation/";
    private static final String UPLOAD_DIR = "/opt/uploads/statutory-allocation/";
    private static final Map<String, String> REQUIRED_DOCUMENTS = new HashMap<>() {{
        put("passport_photos", "Two Passport Photographs");
        put("tax_clearances", "Tax Clearances");
        put("declaration_of_age", "Declaration of Age");
        put("administrative_charges", "Administrative Charges");
        put("fee_receipt", "Processing Fees");
        put("naturalization_doc", "Naturalization Documents");
        put("oath_decorations", "Oath Decorations");
    }};

    private static final Map<String, String> CUST_REQUIRED_DOCUMENTS = new HashMap<>(){{
        put("passport_photos", "Two Passport Photographs");
        put("tax_clearances", "Tax Clearances");
        put("affidavits", "Affidavits");
        put("community_consent_letters", "Community Consent Letters");
        put("development_sketches", "Development Sketches");
    }};
    private final StatutoryAllocationRepository statutoryAllocationRepository;

    public void StatutoryAllocationApplication() throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIR));
    }


//    @Override
//    public LandApplicationResponse applyForLand(LandApplicationRequest landApplicationRequest) {
//        LandApplication landApplication = new LandApplication();
//        landApplication.setApplicantName(landApplicationRequest.getApplicantName());
//        landApplication.setApplicationType(LandApplicationType.COFO);
//        landApplication.setApplicationDate(LocalDateTime.now());
//        landApplication.setStatus(Status.PENDING);
//        landApplicationRepository.save(landApplication);
//        return LandApplicationResponse.builder()
//                .applicationDate(LocalDateTime.now())
//                .applicationType(LandApplicationType.COFO)
//                .status(Status.PENDING)
//                .applicantName(landApplicationRequest.getApplicantName()).build();
//    }
//
//    @Override
//    public List<LandApplicationResponse> getAllApplication() {
//        List<LandApplication> landApplications = landApplicationRepository.findAll();
//        return landApplications.stream().map(landApplication -> LandApplicationResponse.builder()
//                .email(landApplication.getEmail())
//                .applicationType(landApplication.getApplicationType())
//                .applicationDate(landApplication.getApplicationDate())
//                .status(landApplication.getStatus())
//                .build()
//        ).collect(Collectors.toList());
//    }


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

    @Override
    public Map<String, String> customLandApplication(CustomaryAllocationRequest customaryAllocationRequest, Map<String, MultipartFile> documents) throws IOException {
        CustomaryAllocationApplication customaryAllocationApplication = new CustomaryAllocationApplication();
        customaryAllocationApplication.setApplicantName(customaryAllocationRequest.getApplicantName());
        customaryAllocationApplication.setApplicationDate(LocalDate.now());
        customaryAllocationApplication.setApplicationFeeAmount(customaryAllocationRequest.getApplicationFeeAmount());
        customaryAllocationApplication.setLandPurpose(customaryAllocationRequest.getLandPurpose());
        customaryAllocationApplication.setCommunityConsentDate(customaryAllocationRequest.getCommunityConsentDate());
        customaryAllocationApplication.setCommunityLeaderTitle(customaryAllocationRequest.getCommunityLeaderTitle());
        customaryAllocationApplication.setEmail(customaryAllocationRequest.getEmail());
        customaryAllocationApplication.setGender(customaryAllocationRequest.getGender());
        customaryAllocationApplication.setHomeAddress(customaryAllocationRequest.getHomeAddress());
        customaryAllocationApplication.setLga(customaryAllocationRequest.getLga());
        customaryAllocationApplication.setMaritalStatus(customaryAllocationRequest.getMaritalStatus());
        customaryAllocationApplication.setNationality(customaryAllocationRequest.getNationality());
        customaryAllocationApplication.setOccupation(customaryAllocationRequest.getOccupation());
        customaryAllocationApplication.setPhoneNumber(customaryAllocationRequest.getPhoneNumber());
        customaryAllocationApplication.setStateOfOrigin(customaryAllocationRequest.getStateOfOrigin());
        customaryAllocationApplication.setTownOrArea(customaryAllocationRequest.getTownOrArea());
        customaryAllocationApplication.setPurposeDetail(customaryAllocationRequest.getPurposeDetail());
        Map<String, String> response = new HashMap<>();
        for (String key : CUST_REQUIRED_DOCUMENTS.keySet()) {
            MultipartFile file = documents.get(key);
            if (file == null || file.isEmpty()) {
                response.put(key, "Missing " + CUST_REQUIRED_DOCUMENTS.get(key));
                continue;
            }

            // Ensure the upload directory exists
            Path uploadDirPath = Path.of(UPLOAD_DIR);
            Files.createDirectories(uploadDirPath);

            // Build and save the file path
            String filePath = UPLOAD_DIR + key + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), Path.of(filePath));

            response.put(key, "Uploaded Successfully");
            // Save file path to entity
            switch (key) {
                case "passport_photos" -> customaryAllocationApplication.setPassportPhoto(filePath);
                case "tax_clearances" -> customaryAllocationApplication.setTaxClearance(filePath);
                case "affidavits" -> customaryAllocationApplication.setAffidavit(filePath);
                case "community_consent_letters" -> customaryAllocationApplication.setCommunityConsentLetter(filePath);
                case "development_sketches" -> customaryAllocationApplication.setDevelopmentSketch(filePath);

            }
        }

        // Save the allocation record
        customaryAllocationRepository.save(customaryAllocationApplication);
        return response;
    }

    @Override
    public Map<String, String> statutoryallocation(StatutoryApplicationRequest statutoryApplicationRequest, Map<String, MultipartFile> documents) throws IOException {
        StatutoryAllocationApplication allocationApplication = new StatutoryAllocationApplication();
        allocationApplication.setApplicantName(statutoryApplicationRequest.getApplicantName());
        allocationApplication.setApplicationDate(LocalDate.now());
        allocationApplication.setApplicationFeeAmount(statutoryApplicationRequest.getApplicationFeeAmount());
        allocationApplication.setApplicationNo(statutoryApplicationRequest.getApplicationNo());
        allocationApplication.setApplicationFeeType(statutoryApplicationRequest.getApplicationFeeType());
        allocationApplication.setAssignedDate(statutoryApplicationRequest.getAssignedDate());
        allocationApplication.setGender(statutoryApplicationRequest.getGender());
        allocationApplication.setHomeAddress(statutoryApplicationRequest.getHomeAddress());
        allocationApplication.setLga(statutoryApplicationRequest.getLga());
        allocationApplication.setMaritalStatus(statutoryApplicationRequest.getMaritalStatus());
        allocationApplication.setNationality(statutoryApplicationRequest.getNationality());
        allocationApplication.setOccupation(statutoryApplicationRequest.getOccupation());
        allocationApplication.setPhoneNumber(statutoryApplicationRequest.getPhoneNumber());
        allocationApplication.setStateOfOrigin(statutoryApplicationRequest.getStateOfOrigin());
        allocationApplication.setTownOrArea(statutoryApplicationRequest.getTownOrArea());
        allocationApplication.setExistingLandLocation(statutoryApplicationRequest.getExistingLandLocation());
        allocationApplication.setAcquiringAuthority(statutoryApplicationRequest.getAcquiringAuthority());
        allocationApplication.setOwnsStateLand(statutoryApplicationRequest.getOwnsStateLand());
        allocationApplication.setOathDeclaration(statutoryApplicationRequest.getOathDeclaration());
        allocationApplication.setIsLandDeveloped(statutoryApplicationRequest.getIsLandDeveloped());
        allocationApplication.setOtherFeesBreakdown(statutoryApplicationRequest.getOtherFeesBreakdown());
        allocationApplication.setIsAssignorOrAssignee(statutoryApplicationRequest.getIsAssignorOrAssignee());

        Map<String, String> response = new HashMap<>();
        for (String key : REQUIRED_DOCUMENTS.keySet()) {
            MultipartFile file = documents.get(key);
            if (file == null || file.isEmpty()) {
                response.put(key, "Missing " + REQUIRED_DOCUMENTS.get(key));
                continue;
            }

            // Ensure the upload directory exists
            Path uploadDirPath = Path.of(UPLOAD_DIR_);
            Files.createDirectories(uploadDirPath);

            // Build and save the file path
            String filePath = UPLOAD_DIR_ + key + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), Path.of(filePath));

            response.put(key, "Uploaded Successfully");
            // Save file path to entity
            switch (key) {
                case "passport_photos" -> allocationApplication.setPassportPhotos(filePath);
                case "tax_clearances" -> allocationApplication.setTaxClearances(filePath);
                case "declaration_of_age" -> allocationApplication.setDeclarationOfAge(filePath);
                case "administrative_charges" -> allocationApplication.setAdministrativeCharges(filePath);
                case "processing_fees" -> allocationApplication.setFeeReceipt(filePath);
                case "naturalization_doc" -> allocationApplication.setNaturalizationDoc(filePath);
                case "oath_decorations" -> allocationApplication.setOathDeclaration(filePath);
            }
        }

        statutoryApplicationRepository.save(allocationApplication);
        return response;
    }

    @Override
    public List<StatutoryApplicationResponse> getAllStatutoryAllocations() {
        List<StatutoryAllocationApplication> response =  statutoryApplicationRepository.findAll();
        return response.stream().map(statutoryAllocationApplication -> StatutoryApplicationResponse.builder()
                .applicationNo(statutoryAllocationApplication.getApplicationNo())
                .acquiringAuthority(statutoryAllocationApplication.getAcquiringAuthority())
                .applicationFeeAmount(statutoryAllocationApplication.getApplicationFeeAmount())
                .applicantName(statutoryAllocationApplication.getApplicantName())
                .assigneeNameAndAddress(statutoryAllocationApplication.getAssigneeNameAndAddress())
                .luacNo(statutoryAllocationApplication.getLuacNo())
                .compensationStatus(statutoryAllocationApplication.getCompensationStatus())
                .dateOfBirth(LocalDate.now())
                .phoneNumber(statutoryAllocationApplication.getPhoneNumber())
                .compensationPartPayment(statutoryAllocationApplication.getCompensationPartPayment())
                .ownsStateLand(statutoryAllocationApplication.getOwnsStateLand())
                .email(statutoryAllocationApplication.getEmail())
                .gender(statutoryAllocationApplication.getGender())
                .existingLandLocation(statutoryAllocationApplication.getExistingLandLocation())
                .homeAddress(statutoryAllocationApplication.getHomeAddress())
                .isLandDeveloped(statutoryAllocationApplication.getIsLandDeveloped()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<CustomaryAllocationResponse> getAllCustomaryAllocations() {
        List<CustomaryAllocationApplication> customaryAllocationApplications = customaryAllocationRepository.findAll();
        return customaryAllocationApplications.stream().map(customaryAllocation -> CustomaryAllocationResponse.builder()
                .applicantName(customaryAllocation.getApplicantName())
                .applicantTitle(customaryAllocation.getApplicantTitle())
                .applicationFeeAmount(customaryAllocation.getApplicationFeeAmount())
                .communityConsentDate(customaryAllocation.getCommunityConsentDate())
                .applicationDate(LocalDate.now())
                .dateOfBirth(customaryAllocation.getDateOfBirth())
                .gender(customaryAllocation.getGender())
                .homeAddress(customaryAllocation.getHomeAddress())
                .landPurpose(customaryAllocation.getLandPurpose())
                .occupation(customaryAllocation.getOccupation())
                .townOrArea(customaryAllocation.getTownOrArea())
                .applicantTitle(customaryAllocation.getApplicantTitle())
                .stateOfOrigin(customaryAllocation.getStateOfOrigin())
                .email(customaryAllocation.getEmail())
                .maritalStatus(customaryAllocation.getMaritalStatus())
                .lga(customaryAllocation.getLga())
                .nationality(customaryAllocation.getNationality()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public LandApplicationSummaryResponse getAllStatutorySummary() {
        LandApplicationSummaryResponse environment = new LandApplicationSummaryResponse();
        environment.setTotalApproved((int) statutoryAllocationRepository.countByStatus(Status.APPROVED));
        environment.setTotalRejected((int) statutoryAllocationRepository.countByStatus(Status.REJECTED));
        environment.setTotalPending((int) statutoryAllocationRepository.countByStatus(Status.PENDING));
        environment.setTotalReviewed((int) statutoryAllocationRepository.countByStatus(Status.REVIEWED));
        environment.setTotalAppliedRequest(Math.toIntExact(statutoryAllocationRepository.count()));
        return environment;
    }

    @Override
    public LandApplicationSummaryResponse getAllCustomarySummary() {
        LandApplicationSummaryResponse customaryAllocationApplication = new LandApplicationSummaryResponse();
        customaryAllocationApplication.setTotalApproved((int) customaryAllocationRepository.countByStatus(Status.APPROVED));
        customaryAllocationApplication.setTotalRejected((int) customaryAllocationRepository.countByStatus(Status.REJECTED));
        customaryAllocationApplication.setTotalPending((int) customaryAllocationRepository.countByStatus(Status.PENDING));
        customaryAllocationApplication.setTotalReviewed((int) customaryAllocationRepository.countByStatus(Status.REVIEWED));
        customaryAllocationApplication.setTotalAppliedRequest(Math.toIntExact(customaryAllocationRepository.count()));
        return null;
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