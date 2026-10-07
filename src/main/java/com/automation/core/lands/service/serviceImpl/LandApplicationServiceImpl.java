package com.automation.core.lands.service.serviceImpl;

import com.automation.core.commerce.dto.response.LandApplicationSummaryResponse;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.tracking.enums.ApplicationStatus;
import com.automation.core.tracking.service.ApplicationTrackingService;
import com.automation.core.tracking.enums.ActionType;

import com.automation.core.inspection.model.Inspection;
import com.automation.core.inspection.repository.InspectionRepository;
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
import com.automation.core.lands.service.service.StorageService;
import com.automation.util.enums.GlobalStatus;
import com.automation.util.enums.LandApplicationType;
import com.automation.util.enums.ReportType;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Service
public class LandApplicationServiceImpl implements LandApplicationService {

    private final LandApplicationRepository landApplicationRepository;
    private final StatutoryApplicationRepository statutoryApplicationRepository;
    private final StorageService storageService;
    private final CustomaryAllocationRepository customaryAllocationRepository;
    private final InspectionRepository inspectionRepository;
    private final UserRepository userRepository;
    private final ApplicationTrackingService trackingService;
    private final StatutoryAllocationRepository statutoryAllocationRepositoryAlloc;

    public LandApplicationServiceImpl(
            LandApplicationRepository landApplicationRepository,
            StatutoryApplicationRepository statutoryApplicationRepository,
            @Qualifier("cloudStorageService") StorageService storageService,
            CustomaryAllocationRepository customaryAllocationRepository,
            InspectionRepository inspectionRepository,
            UserRepository userRepository,
            ApplicationTrackingService trackingService,
            StatutoryAllocationRepository statutoryAllocationRepositoryAlloc) {

        this.landApplicationRepository = landApplicationRepository;
        this.statutoryApplicationRepository = statutoryApplicationRepository;
        this.storageService = storageService;
        this.customaryAllocationRepository = customaryAllocationRepository;
        this.inspectionRepository = inspectionRepository;
        this.userRepository = userRepository;
        this.trackingService = trackingService;
        this.statutoryAllocationRepositoryAlloc =
                statutoryAllocationRepositoryAlloc;
    }

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

    @Override
    public Map<String, String> submitCustomaryApplication(CustomaryAllocationRequest request, Map<String, MultipartFile> files) throws IOException {
        CustomaryAllocationApplication entity = new CustomaryAllocationApplication();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setApplicationDate(LocalDate.now());
        entity.setApplicationFeeAmount(request.getApplicationFeeAmount());
        entity.setLandPurpose(request.getLandPurpose());
        entity.setCommunityConsentDate(request.getCommunityConsentDate());
        entity.setCommunityLeaderTitle(request.getCommunityLeaderTitle());
        entity.setEmail(request.getEmail());
        entity.setGender(request.getGender());
        entity.setHomeAddress(request.getHomeAddress());
        entity.setLga(request.getLga());
        entity.setMaritalStatus(request.getMaritalStatus());
        entity.setNationality(request.getNationality());
        entity.setOccupation(request.getOccupation());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setStateOfOrigin(request.getStateOfOrigin());
        entity.setTownOrArea(request.getTownOrArea());
        entity.setPurposeDetail(request.getPurposeDetail());
        entity.setStatus(Status.PENDING);
        entity = customaryAllocationRepository.save(entity);

        Map<String, String> uploadResults = new HashMap<>();
        if (files != null) {
            for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
                String fieldName = entry.getKey();
                MultipartFile file = entry.getValue();
                if (file != null && !file.isEmpty()) {
                    String url = storageService.store(file, entity.getId(), fieldName);
                    uploadResults.put(fieldName, "Uploaded Successfully");
                    switch (fieldName) {
                        case "passportPhoto", "passport_photos" -> entity.setPassportPhoto(url);
                        case "taxClearance", "tax_clearances" -> entity.setTaxClearance(url);
                        case "affidavit", "affidavits" -> entity.setAffidavit(url);
                        case "communityConsentLetter", "community_consent_letters" -> entity.setCommunityConsentLetter(url);
                        case "developmentSketch", "development_sketches" -> entity.setDevelopmentSketch(url);
                    }
                }
            }
        }
        customaryAllocationRepository.save(entity);

        User user = userRepository.findByEmail(entity.getEmail()).orElse(null);
        if (user != null) {
            trackingService.registerApplication("LANDS_CUSTOMARY", entity.getId(), user);
        }
        Inspection inspection = new Inspection();
        inspection.setRequestId(UUID.randomUUID());
        inspection.setSourceService("CUSTOMARY LAND ALLOCATION");
        inspection.setApplicantName(entity.getFirstName() + " " + entity.getLastName());
        inspection.setApplicationType(entity.getLandPurpose());
        inspection.setCustomaryAllocationApplication(entity);
        inspection.setStatus(Status.PENDING);
        inspection.setCreatedAt(LocalDateTime.now());
        inspectionRepository.save(inspection);

        return uploadResults;
    }

    @Override
    public Map<String, String> submitStatutoryApplication(StatutoryApplicationRequest request, Map<String, MultipartFile> files) throws IOException {
        StatutoryAllocationApplication entity = new StatutoryAllocationApplication();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setApplicationDate(LocalDate.now());
        entity.setApplicationFeeAmount(request.getApplicationFeeAmount());
        entity.setApplicationNo(request.getApplicationNo());
        entity.setApplicationFeeType(request.getApplicationFeeType());
        entity.setAssignedDate(request.getAssignedDate());
        entity.setGender(request.getGender());
        entity.setHomeAddress(request.getHomeAddress());
        entity.setLga(request.getLga());
        entity.setMaritalStatus(request.getMaritalStatus());
        entity.setNationality(request.getNationality());
        entity.setOccupation(request.getOccupation());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setStateOfOrigin(request.getStateOfOrigin());
        entity.setTownOrArea(request.getTownOrArea());
        entity.setExistingLandLocation(request.getExistingLandLocation());
        entity.setAcquiringAuthority(request.getAcquiringAuthority());
        entity.setOwnsStateLand(request.getOwnsStateLand());
        entity.setIsLandDeveloped(request.getIsLandDeveloped());
        entity.setOtherFeesBreakdown(request.getOtherFeesBreakdown());
        entity.setIsAssignorOrAssignee(request.getIsAssignorOrAssignee());
        entity.setStatus(Status.PENDING);
        entity = statutoryApplicationRepository.save(entity);

        Map<String, String> uploadResults = new HashMap<>();
        if (files != null) {
            for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
                String fieldName = entry.getKey();
                MultipartFile file = entry.getValue();
                if (file != null && !file.isEmpty()) {
                    String url = storageService.store(file, entity.getId(), fieldName);
                    uploadResults.put(fieldName, "Uploaded Successfully");
                    switch (fieldName) {
                        case "passportPhoto" -> entity.setPassportPhotos(url);
                        case "taxClearance" -> entity.setTaxClearances(url);
                        case "feeReceipt" -> entity.setFeeReceipt(url);
                        case "ageDeclaration" -> entity.setDeclarationOfAge(url);
                        case "naturalizationDoc" -> entity.setNaturalizationDoc(url);
                        case "oathDeclaration" -> entity.setOathDeclaration(url);
                    }
                }
            }
        }
        statutoryApplicationRepository.save(entity);

        User user = userRepository.findByEmail(entity.getEmail()).orElse(null);
        if (user != null) {
            trackingService.registerApplication("LANDS_STATUTORY", entity.getId(), user);
        }

        return uploadResults;
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
    }

    @Override
    public Map<String, String> customLandApplication(CustomaryAllocationRequest customaryAllocationRequest, Map<String, MultipartFile> documents) throws IOException {
        return submitCustomaryApplication(customaryAllocationRequest, documents);
    }

    @Override
    public Map<String, String> statutoryallocation(StatutoryApplicationRequest statutoryApplicationRequest, Map<String, MultipartFile> documents) throws IOException {
        return submitStatutoryApplication(statutoryApplicationRequest, documents);
    }

    @Override
    public List<StatutoryApplicationResponse> getAllStatutoryAllocations() {
        List<StatutoryAllocationApplication> response =  statutoryApplicationRepository.findAll();
        return response.stream().map(statutoryAllocationApplication -> StatutoryApplicationResponse.builder()
                .applicationNo(statutoryAllocationApplication.getApplicationNo())
                .acquiringAuthority(statutoryAllocationApplication.getAcquiringAuthority())
                .applicationFeeAmount(statutoryAllocationApplication.getApplicationFeeAmount())
                .firstName(statutoryAllocationApplication.getFirstName())
                .lastName(statutoryAllocationApplication.getLastName())
                .assigneeNameAndAddress(statutoryAllocationApplication.getAssigneeNameAndAddress())
                .luacNo(statutoryAllocationApplication.getLuacNo())
                .compensationStatus(statutoryAllocationApplication.getCompensationStatus())
                .applicationDate(statutoryAllocationApplication.getApplicationDate())
                .phoneNumber(statutoryAllocationApplication.getPhoneNumber())
                .title(statutoryAllocationApplication.getTitle())
                .titleOther(statutoryAllocationApplication.getTitleOther())
                .stateOfOrigin(statutoryAllocationApplication.getStateOfOrigin())
                .nationality(statutoryAllocationApplication.getNationality())
                .placeOfBirth(statutoryAllocationApplication.getPlaceOfBirth())
                .gpsAccuracy(statutoryAllocationApplication.getGpsAccuracy())
                .lga(statutoryAllocationApplication.getLga())
                .status(statutoryAllocationApplication.getStatus())
                .existingTitleNo(statutoryAllocationApplication.getExistingTitleNo())
                .existingLandLocation(statutoryAllocationApplication.getExistingLandLocation())
                .isAssignorOrAssignee(statutoryAllocationApplication.getIsAssignorOrAssignee())
                .plotType(statutoryAllocationApplication.getPlotType())
                .plotTypeDetail(statutoryAllocationApplication.getPlotTypeDetail())
                .longitude(statutoryAllocationApplication.getLongitude())
                .latitude(statutoryAllocationApplication.getLatitude())
                .altitude(statutoryAllocationApplication.getAltitude())
                .compensationPartPayment(statutoryAllocationApplication.getCompensationPartPayment())
                .ownsStateLand(statutoryAllocationApplication.getOwnsStateLand())
                .email(statutoryAllocationApplication.getEmail())
                .gender(statutoryAllocationApplication.getGender())
                .homeAddress(statutoryAllocationApplication.getHomeAddress())
                .passportPhoto(statutoryAllocationApplication.getPassportPhotos())
                .taxClearance(statutoryAllocationApplication.getTaxClearances())
                .feeReceipt(statutoryAllocationApplication.getFeeReceipt())
                .ageDeclaration(statutoryAllocationApplication.getDeclarationOfAge())
                .naturalizationDoc(statutoryAllocationApplication.getNaturalizationDoc())
                .oathDeclaration(statutoryAllocationApplication.getOathDeclaration())
                .swornDeclaration(statutoryAllocationApplication.getSwornDeclaration())
                .acquiringAuthority(statutoryAllocationApplication.getAcquiringAuthority())
                .proposedInvestment(statutoryAllocationApplication.getProposedInvestment())
                .dateOfBirth(statutoryAllocationApplication.getDateOfBirth())
                .declarationDate(statutoryAllocationApplication.getDeclarationDate())
                .investmentFinancing(statutoryAllocationApplication.getInvestmentFinancing())
                .memorandumArticlesPath(statutoryAllocationApplication.getMemorandumArticlesPath())
                .townOrArea(statutoryAllocationApplication.getTownOrArea())
                .applicationFeeType(statutoryAllocationApplication.getApplicationFeeType())
                .previousAcquisitionDate(statutoryAllocationApplication.getPreviousAcquisitionDate())
                .plotSizeOther(statutoryAllocationApplication.getPlotSizeOther())
                .residentialBuildingType(statutoryAllocationApplication.getResidentialBuildingType())
                .nationalityOther(statutoryAllocationApplication.getNationalityOther())
                .maritalStatus(statutoryAllocationApplication.getMaritalStatus())
                .occupation(statutoryAllocationApplication.getOccupation())
                .assignedDate(statutoryAllocationApplication.getAssignedDate())
                .isLandDeveloped(statutoryAllocationApplication.getIsLandDeveloped()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<CustomaryAllocationResponse> getAllCustomaryAllocations() {
        List<CustomaryAllocationApplication> customaryAllocationApplications = customaryAllocationRepository.findAll();
        return customaryAllocationApplications.stream().map(customaryAllocation -> CustomaryAllocationResponse.builder()
                .firstName(customaryAllocation.getFirstName())
                .lastName(customaryAllocation.getLastName())
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
                .status(customaryAllocation.getStatus())
                .passportPhoto(customaryAllocation.getPassportPhoto())
                .taxClearance(customaryAllocation.getTaxClearance())
                .affidavit(customaryAllocation.getAffidavit())
                .communityConsent(customaryAllocation.getCommunityConsent())
                .proposedBuildingType(customaryAllocation.getProposedBuildingType())
                .proposedDevelopmentCost(customaryAllocation.getProposedDevelopmentCost())
                .landPurpose(customaryAllocation.getLandPurpose())
                .existingLandLocation(customaryAllocation.getExistingLandLocation())
                .purposeDetail(customaryAllocation.getPurposeDetail())
                .latitude(customaryAllocation.getLatitude())
                .longitude(customaryAllocation.getLongitude())
                .altitude(customaryAllocation.getAltitude())
                .developmentSketch(customaryAllocation.getDevelopmentSketch())
                .nationality(customaryAllocation.getNationality()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public LandApplicationSummaryResponse getAllStatutorySummary() {
        LandApplicationSummaryResponse environment = new LandApplicationSummaryResponse();
        environment.setTotalApproved((int) statutoryAllocationRepositoryAlloc.countByStatus(Status.APPROVED));
        environment.setTotalRejected((int) statutoryAllocationRepositoryAlloc.countByStatus(Status.REJECTED));
        environment.setTotalPending((int) statutoryAllocationRepositoryAlloc.countByStatus(Status.PENDING));
        environment.setTotalReviewed((int) statutoryAllocationRepositoryAlloc.countByStatus(Status.REVIEWED));
        environment.setTotalAppliedRequest(Math.toIntExact(statutoryAllocationRepositoryAlloc.count()));
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
        return customaryAllocationApplication;
    }

    @Override
    public void uploadFilesCustomary(Long id,
                                    MultipartFile passportPhoto,
                                    MultipartFile taxClearance,
                                    MultipartFile affidavit,
                                    MultipartFile communityConsentLetter,
                                    MultipartFile developmentSketch
                            ) throws IOException {
        CustomaryAllocationApplication entity = customaryAllocationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Form id not found"));

        if (passportPhoto != null && !passportPhoto.isEmpty()) {
            String path = storageService.store(passportPhoto, id, "passportPhoto");
            entity.setPassportPhoto(path);
        }
        if (taxClearance != null && !taxClearance.isEmpty()) {
            entity.setTaxClearance(storageService.store(taxClearance, id, "taxClearance"));
        }
        if (affidavit != null && !affidavit.isEmpty()) {
            entity.setAffidavit(storageService.store(affidavit, id, "affidavit"));
        }
        if (communityConsentLetter != null && !communityConsentLetter.isEmpty()) {
            entity.setCommunityConsentLetter(storageService.store(communityConsentLetter, id, "communityConsentLetter"));
        }
        if (developmentSketch != null && !developmentSketch.isEmpty()) {
            entity.setDevelopmentSketch(storageService.store(developmentSketch, id, "developmentSketch"));
        }
        customaryAllocationRepository.save(entity);
    }

    @Override
    public void uploadFilesStatutory(Long formId, MultipartFile passportPhoto, MultipartFile taxClearance, MultipartFile feeReceipt, MultipartFile ageDeclaration, MultipartFile naturalizationDoc, MultipartFile oathDeclaration) throws IOException {
        StatutoryAllocationApplication entity = statutoryApplicationRepository.findById(formId)
                .orElseThrow(() -> new NoSuchElementException("Form id not found"));

        if (passportPhoto != null && !passportPhoto.isEmpty()) {
            String path = storageService.store(passportPhoto, formId, "passportPhoto");
            entity.setPassportPhotos(path);
        }
        if (taxClearance != null && !taxClearance.isEmpty()) {
            entity.setTaxClearances(storageService.store(taxClearance, formId, "taxClearance"));
        }
        if (feeReceipt != null && !feeReceipt.isEmpty()) {
            entity.setFeeReceipt(storageService.store(feeReceipt, formId, "feeReceipt"));
        }
        if (ageDeclaration != null && !ageDeclaration.isEmpty()) {
            entity.setDeclarationOfAge(storageService.store(ageDeclaration, formId, "ageDeclaration"));
        }
        if (naturalizationDoc != null && !naturalizationDoc.isEmpty()) {
            entity.setNaturalizationDoc(storageService.store(naturalizationDoc, formId, "naturalizationDoc"));
        }
        if (oathDeclaration != null && !oathDeclaration.isEmpty()) {
            entity.setOathDeclaration(storageService.store(oathDeclaration, formId, "oathDeclaration"));
        }

        statutoryApplicationRepository.save(entity);
    }

    @Override
    public Long saveFormRequest(CustomaryAllocationRequest formRequest) {
        CustomaryAllocationApplication entity = new CustomaryAllocationApplication();
        entity.setApplicationDate(LocalDate.now());
        entity.setStatus(Status.PENDING);
        entity.setPurposeDetail(formRequest.getPurposeDetail());
        entity.setLga(formRequest.getLga());
        entity.setNationality(formRequest.getNationality());
        entity.setApplicantTitle(formRequest.getApplicantTitle());
        entity.setStateOfOrigin(formRequest.getStateOfOrigin());
        entity.setEmail(formRequest.getEmail());
        entity.setMaritalStatus(formRequest.getMaritalStatus());
        entity.setNationality(formRequest.getNationality());
        entity.setStateOfOrigin(formRequest.getStateOfOrigin());
        entity.setEmail(formRequest.getEmail());
        entity.setFirstName(formRequest.getFirstName());
        entity.setLastName(formRequest.getLastName());
        entity.setIsPayed(formRequest.getIsPayed());
        entity.setGender(formRequest.getGender());
        entity.setTownOrArea(formRequest.getTownOrArea());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setProposedBuildingType(formRequest.getProposedBuildingType());
        entity.setProposedDevelopmentCost(formRequest.getProposedDevelopmentCost());
        entity.setLandPurpose(formRequest.getLandPurpose());
        entity.setOccupation(formRequest.getOccupation());
        entity.setTownOrArea(formRequest.getTownOrArea());
        entity.setHomeAddress(formRequest.getHomeAddress());
        entity.setExistingLandLocation(formRequest.getExistingLandLocation());
        entity.setCommunityLeaderTitle(formRequest.getCommunityLeaderTitle());
        entity = customaryAllocationRepository.save(entity);

        User user = userRepository.findByEmail(entity.getEmail()).orElse(null);
        if (user != null) {
            trackingService.registerApplication("LANDS_CUSTOMARY", entity.getId(), user);
        }

        Inspection inspection = new Inspection();
        inspection.setRequestId(UUID.randomUUID());
        inspection.setSourceService("CUSTOMARY LAND ALLOCATION");
        inspection.setApplicantName(formRequest.getFirstName() +" "+ formRequest.getLastName());
        inspection.setApplicationType(formRequest.getLandPurpose());
        inspection.setCustomaryAllocationApplication(entity);
        inspection.setStatus(Status.PENDING);
        inspection.setCreatedAt(LocalDateTime.now());
        inspectionRepository.save(inspection);

        return entity.getId();
    }

    @Override
    public Long saveStatutoryFormRequest(StatutoryApplicationRequest formRequest) {
        StatutoryAllocationApplication entity = new StatutoryAllocationApplication();
        entity.setApplicationNo(formRequest.getApplicationNo());
        entity.setStatus(Status.PENDING);
        entity.setFirstName(formRequest.getFirstName());
        entity.setLastName(formRequest.getLastName());
        entity.setAssignedDate(LocalDate.now());
        entity.setApplicationDate(LocalDate.now());
        entity.setGpsAccuracy(formRequest.getGpsAccuracy());
        entity.setLatitude(formRequest.getLatitude());
        entity.setLongitude(formRequest.getLongitude());
        entity.setAltitude(formRequest.getAltitude());
        entity.setIsAssignorOrAssignee(formRequest.getIsAssignorOrAssignee());
        entity.setApplicationFeeAmount(formRequest.getApplicationFeeAmount());
        entity.setOtherFeesBreakdown(formRequest.getOtherFeesBreakdown());
        entity.setGender(formRequest.getGender());
        entity.setHomeAddress(formRequest.getHomeAddress());
        entity.setLga(formRequest.getLga());
        entity.setNationality(formRequest.getNationality());
        entity.setOccupation(formRequest.getOccupation());
        entity.setTownOrArea(formRequest.getTownOrArea());
        entity.setTitle(formRequest.getTitle());
        entity.setLuacNo(formRequest.getLuacNo());
        entity.setOwnsStateLand(formRequest.getOwnsStateLand());
        entity.setIsLandDeveloped(formRequest.getIsLandDeveloped());
        entity.setStateOfOrigin(formRequest.getStateOfOrigin());
        entity.setEmail(formRequest.getEmail());
        entity.setMaritalStatus(formRequest.getMaritalStatus());
        entity.setPhoneNumber(formRequest.getPhoneNumber());
        entity.setSwornDeclaration(formRequest.getSwornDeclaration());
        entity.setResidentialBuildingType(formRequest.getResidentialBuildingType());
        entity.setPlotType(formRequest.getPlotType());
        entity.setApplicationFeeType(formRequest.getApplicationFeeType());
        entity.setPreviousAcquisitionDate(formRequest.getPreviousAcquisitionDate());
        entity.setAssigneeNameAndAddress(formRequest.getAssigneeNameAndAddress());
        entity.setAssignedDate(LocalDate.now());
        entity.setIsPayed(formRequest.getIsPayed());
        entity.setPlaceOfBirth(formRequest.getPlaceOfBirth());
        entity = statutoryApplicationRepository.save(entity);

        Inspection inspection = new Inspection();
        inspection.setRequestId(UUID.randomUUID());
        inspection.setSourceService("STATUTORY LAND ALLOCATION");
        inspection.setApplicantName(formRequest.getFirstName() +" "+ formRequest.getLastName());
        inspection.setApplicationType(formRequest.getApplicationFeeType());
        inspection.setStatutoryAllocationApplication(entity);
        inspection.setStatus(Status.PENDING);
        inspection.setCreatedAt(LocalDateTime.now());
        inspectionRepository.save(inspection);

        return entity.getId();
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
                .certificateUrl(app.getCertificateUrl())
                .build();
    }
}
