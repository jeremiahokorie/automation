package com.automation.core.lands.service.serviceImpl;

import com.automation.core.basepa.dto.request.ApprovalRequest;
import com.automation.core.basepa.dto.response.ApprovalResponse;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.lands.dto.response.StatutoryAllocationResponse;
import com.automation.core.lands.model.CertificateOfOccupancy;
import com.automation.core.lands.model.GroundRent;
import com.automation.core.lands.model.StatutoryAllocation;
import com.automation.core.lands.repository.StatutoryAllocationRepository;
import com.automation.core.lands.service.service.ReportService;
import com.automation.core.lands.service.service.StatutoryAllocationService;
import com.automation.util.ReportUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatutoryAllocationServiceImpl implements StatutoryAllocationService, ReportService {

    private final StatutoryAllocationRepository statutoryAllocationRepository;

    private static final String UPLOAD_DIR = "/opt/uploads/statutory-allocation/";
    private static final Map<String, String> REQUIRED_DOCUMENTS = new HashMap<>() {{
        put("passport_photos", "Two Passport Photographs");
        put("tax_clearances", "Tax Clearances");
        put("declaration_of_age", "Declaration of Age");
        put("administrative_charges", "Administrative Charges");
        put("processing_fees", "Processing Fees");
    }};

    public void StatutoryAllocationService() throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIR));
    }

    public Map<String, String> uploadDocuments(String applicantName, Map<String, MultipartFile> documents) throws IOException {
        StatutoryAllocation allocation = new StatutoryAllocation();
        allocation.setApplicantName(applicantName);
        allocation.setCreatedAt(LocalDateTime.now());
        allocation.setStatus(Status.PENDING);

        Map<String, String> response = new HashMap<>();
        for (String key : REQUIRED_DOCUMENTS.keySet()) {
            MultipartFile file = documents.get(key);
            if (file == null || file.isEmpty()) {
                response.put(key, "Missing " + REQUIRED_DOCUMENTS.get(key));
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
                case "passport_photos" -> allocation.setPassportPhotos(filePath);
                case "tax_clearances" -> allocation.setTaxClearances(filePath);
                case "declaration_of_age" -> allocation.setDeclarationOfAge(filePath);
                case "administrative_charges" -> allocation.setAdministrativeCharges(filePath);
                case "processing_fees" -> allocation.setProcessingFees(filePath);
            }
        }

        // Save the allocation record
        statutoryAllocationRepository.save(allocation);
        return response;
    }

    public List<StatutoryAllocationResponse> getAllAllocations() {
        List<StatutoryAllocation> statutoryAllocations = statutoryAllocationRepository.findAll();
        return statutoryAllocations.stream().map(statutoryAllocation -> StatutoryAllocationResponse.builder()
                        .id(statutoryAllocation.getId())
                        .createdAt(statutoryAllocation.getCreatedAt())
                        .administrativeCharges(statutoryAllocation.getAdministrativeCharges())
                        .applicantName(statutoryAllocation.getApplicantName())
                        .passportPhotos(statutoryAllocation.getPassportPhotos())
                        .processingFees(statutoryAllocation.getProcessingFees())
                        .taxClearances(statutoryAllocation.getTaxClearances())
                        .status(statutoryAllocation.getStatus())
                        .declarationOfAge(statutoryAllocation.getDeclarationOfAge()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public byte[] generateReport(LocalDate startDate, LocalDate endDate, ReportType reportType) {
        List<StatutoryAllocation> records = statutoryAllocationRepository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());

        // Generate and return report file (PDF, Excel, etc.)
        return ReportUtil.generatePdfReportFromStatutory(records, reportType); // Utility method
    }

    @Override
    public ApprovalResponse approveStatutory(Long id, ApprovalRequest commentRequest) {
        StatutoryAllocation statutoryAllocation = statutoryAllocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));

        statutoryAllocation.setStatus(Status.APPROVED);
        statutoryAllocation.setComment(commentRequest.getComment());
        statutoryAllocation.setApprovalDate(LocalDate.now());
        statutoryAllocationRepository.save(statutoryAllocation);
        return ApprovalResponse.builder()
                .comment(statutoryAllocation.getComment())
                .build();
    }

    @Override
    public ApprovalResponse rejectStatutory(Long id, ApprovalRequest commentRequest) {
        StatutoryAllocation rejectStatutoryApplication = statutoryAllocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
        rejectStatutoryApplication.setStatus(Status.REJECTED);
        rejectStatutoryApplication.setComment(commentRequest.getComment());
        rejectStatutoryApplication.setRejectionDate(LocalDate.now());
        statutoryAllocationRepository.save(rejectStatutoryApplication);
        return ApprovalResponse.builder()
                .comment(rejectStatutoryApplication.getComment())
                .id(rejectStatutoryApplication.getId())
                .build();
    }

}
