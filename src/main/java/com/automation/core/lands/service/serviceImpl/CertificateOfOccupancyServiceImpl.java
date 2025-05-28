package com.automation.core.lands.service.serviceImpl;

import com.automation.core.global.exception.Exception;
import com.automation.core.lands.dto.response.CertificateResponse;
import com.automation.core.lands.dto.response.StatutoryAllocationResponse;
import com.automation.core.lands.model.CertificateOfOccupancy;
import com.automation.core.lands.model.StatutoryAllocation;
import com.automation.core.lands.repository.CertificateOfOccupancyRepository;
import com.automation.core.lands.service.service.CertificateOfOccupancyService;
import com.automation.core.lands.service.service.ReportService;
import com.automation.util.ReportUtil;
import com.automation.util.enums.ReportType;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import static javax.swing.UIManager.put;

@RequiredArgsConstructor
@Service
public class CertificateOfOccupancyServiceImpl implements CertificateOfOccupancyService, ReportService {

    private static final String UPLOAD_DIR = "/opt/uploads/certificate-of-occupancy/";


    private static final Map<String, String> REQUIRED_DOCUMENTS = new HashMap<>() {{
        put("district_head_letter", "District Head Letter");
        put("sales_agreement", "Sales Agreement");
        put("declaration_of_age", "Declaration of Age");
        put("tax_clearance", "Tax Clearance");
        put("survey_data", "Survey Data");
        put("local_government_confirmation_letter", "Local Government Confirmation Letter");
    }};

    @Autowired
    private CertificateOfOccupancyRepository repository;

    public void CertificateOfOccupancyService() throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIR));
    }

    public Map<String, String> uploadDocuments(String applicantName, Map<String, MultipartFile> documents) throws IOException {
        CertificateOfOccupancy allocation = new CertificateOfOccupancy();
        allocation.setApplicantName(applicantName);
        allocation.setCreatedAt(LocalDateTime.now());
        allocation.setStatus(Status.PENDING);
//        allocation.setApplicantEmail(applicantEmail);

        Map<String, String> response = new HashMap<>();

        try {
            for (String key : REQUIRED_DOCUMENTS.keySet()) {
                MultipartFile file = documents.get(key);
                if (file == null || file.isEmpty()) {
                    response.put(key, "Missing " + REQUIRED_DOCUMENTS.get(key));
                    continue;
                }

                Path uploadDirPath = Path.of(UPLOAD_DIR);
                Files.createDirectories(uploadDirPath);

                String filePath = UPLOAD_DIR + key + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Files.copy(file.getInputStream(), Path.of(filePath));

                response.put(key, "Uploaded Successfully");

                // Save file path to entity
                switch (key) {
                    case "district_head_letter" -> allocation.setDistrictHeadLetter(filePath);
                    case "sales_agreement" -> allocation.setSalesAgreement(filePath);
                    case "declaration_of_age" -> allocation.setDeclarationOfAge(filePath);
                    case "tax_clearance" -> allocation.setTaxClearance(filePath);
                    case "survey_data" -> allocation.setSurveyData(filePath);
                    case "local_government_confirmation_letter" ->
                            allocation.setLocalGovernmentConfirmationLetter(filePath);
                }
            }
        }catch (Exception e){
            throw new Exception("Error occured while uploading documents");
        }

        repository.save(allocation);
        return response;
    }


     public List<CertificateResponse> getCofOs() {
        List<CertificateOfOccupancy> certificate = repository.findAll();
        return certificate.stream().map(cofos -> CertificateResponse.builder()
                .id(cofos.getId())
                .createdAt(cofos.getCreatedAt())
                .applicantName(cofos.getApplicantName())
                .taxClearance(cofos.getTaxClearance())
                .districtHeadLetter(cofos.getDistrictHeadLetter())
                .localGovernmentConfirmationLetter(cofos.getLocalGovernmentConfirmationLetter())
                .salesAgreement(cofos.getSalesAgreement())
                .surveyData(cofos.getSurveyData())
                .status(cofos.getStatus())
                .declarationOfAge(cofos.getDeclarationOfAge()).build()
        ).collect(Collectors.toList());
    }


    @Override
    public byte[] generateReport(LocalDate startDate, LocalDate endDate, ReportType reportType) {
        List<CertificateOfOccupancy> records = repository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());

        // Generate and return report file (PDF, Excel, etc.)
        return ReportUtil.generatePdfReportFromCofO(records, reportType); // Utility method
    }
}
