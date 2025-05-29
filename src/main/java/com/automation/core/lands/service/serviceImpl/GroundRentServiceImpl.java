package com.automation.core.lands.service.serviceImpl;

import com.automation.core.basepa.dto.request.ApprovalRequest;
import com.automation.core.basepa.dto.response.ApprovalResponse;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.lands.dto.response.GroundRentResponse;
import com.automation.core.lands.model.CertificateOfOccupancy;
import com.automation.core.lands.model.GroundRent;
import com.automation.core.lands.model.StatutoryAllocation;
import com.automation.core.lands.repository.GroundRentRepository;
import com.automation.core.lands.service.service.GroundRentService;
import com.automation.core.lands.service.service.ReportService;
import com.automation.util.ReportUtil;
import com.automation.util.enums.ReportType;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GroundRentServiceImpl implements GroundRentService, ReportService {

    private static final String UPLOAD_DIR = "/opt/uploads/ground-rent/";
    private final GroundRentRepository groundRentRepository;

    @Override
    public Map<String, String> submitform(String baNo, String landNo, String record, Double rent, MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        try {
            GroundRent groundRent = new GroundRent();
            groundRent.setBaNo(baNo);
            groundRent.setLandNo(landNo);
            groundRent.setRecord(record);
            groundRent.setRent(rent);
            groundRent.setStatus(Status.PENDING);

            // Check and save file if provided

            if (file != null && !file.isEmpty()) {
                String uploadDir = UPLOAD_DIR;
                Files.createDirectories(Path.of(uploadDir));

                String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Files.copy(file.getInputStream(), Path.of(filePath));

                groundRent.setOptionalFile(filePath);
                response.put("file", "Uploaded successfully");
            } else {
                response.put("file", "No file uploaded (optional)");
            }
            groundRentRepository.save(groundRent);
            response.put("status", "Form submitted successfully");

        } catch (IOException e) {
            e.printStackTrace();
            response.put("error", "Failed to process form: " + e.getMessage());
        }
        return response;
    }


    @Override
    public List<GroundRentResponse> getCofOs() {
        List<GroundRent> groundrent = groundRentRepository.findAll();
        return groundrent.stream().map(rent -> GroundRentResponse.builder()
                .id(rent.getId())
                .status(rent.getStatus())
                .baNo(rent.getBaNo())
                .landNo(rent.getLandNo())
                .record(rent.getRecord())
                .rent(rent.getRent())
                .optionalFile(rent.getOptionalFile())
                .createdAt(rent.getCreatedAt())
                .build()
        ).collect(Collectors.toList());
    }



    @Override
    public byte[] generateReport(LocalDate startDate, LocalDate endDate, ReportType reportType) {
        List<GroundRent> records = groundRentRepository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());

        // Generate and return report file (PDF, Excel, etc.)
        return ReportUtil.generatePdfReportFromGroundRent(records, reportType); // Utility method
    }


    @Override
    public ApprovalResponse approveGroundRent(Long id, ApprovalRequest commentRequest) {
        GroundRent permit = groundRentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));

        permit.setStatus(Status.APPROVED);
        permit.setComment(commentRequest.getComment());
        permit.setApprovalDate(LocalDate.now());
        groundRentRepository.save(permit);
        return ApprovalResponse.builder()
                .comment(permit.getComment())
                .build();
    }

    @Override
    public ApprovalResponse rejectGrounRent(Long id, ApprovalRequest commentRequest) {
        GroundRent rejectLandApplication = groundRentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
        rejectLandApplication.setStatus(Status.REJECTED);
        rejectLandApplication.setComment(commentRequest.getComment());
        rejectLandApplication.setRejectionDate(LocalDate.now());
        groundRentRepository.save(rejectLandApplication);
        return ApprovalResponse.builder()
                .comment(rejectLandApplication.getComment())
                .id(rejectLandApplication.getId())
                .build();
    }
}
