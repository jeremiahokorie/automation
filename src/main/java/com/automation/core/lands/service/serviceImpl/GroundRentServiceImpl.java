package com.automation.core.lands.service.serviceImpl;

import com.automation.core.lands.dto.request.GroundRentRequest;
import com.automation.core.lands.dto.response.GroundRentResponse;
import com.automation.core.lands.dto.response.StatutoryAllocationResponse;
import com.automation.core.lands.model.GroundRent;
import com.automation.core.lands.model.StatutoryAllocation;
import com.automation.core.lands.repository.GroundRentRepository;
import com.automation.core.lands.service.service.GrountRentService;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GroundRentServiceImpl implements GrountRentService {

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

}
