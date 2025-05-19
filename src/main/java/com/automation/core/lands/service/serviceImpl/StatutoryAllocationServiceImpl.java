package com.automation.core.lands.service.serviceImpl;

import com.automation.core.lands.model.StatutoryAllocation;
import com.automation.core.lands.repository.StatutoryAllocationRepository;
import com.automation.core.lands.service.service.StatutoryAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StatutoryAllocationServiceImpl implements StatutoryAllocationService {

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

        Map<String, String> response = new HashMap<>();
        for (String key : REQUIRED_DOCUMENTS.keySet()) {
            MultipartFile file = documents.get(key);
            if (file == null || file.isEmpty()) {
                response.put(key, "Missing " + REQUIRED_DOCUMENTS.get(key));
                continue;
            }
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

    public List<StatutoryAllocation> getAllAllocations() {
        return statutoryAllocationRepository.findAll();
    }

}
