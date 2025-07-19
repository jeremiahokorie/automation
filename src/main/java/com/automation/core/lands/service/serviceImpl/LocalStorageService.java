package com.automation.core.lands.service.serviceImpl;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.nio.file.Paths.*;

@Service
public class LocalStorageService {

   // private static final String UPLOAD_DIR = "/opt/uploads/certificate-of-occupancy/";
    private final Path rootLocation = get("uploads");

    public LocalStorageService() throws IOException {
        Files.createDirectories(rootLocation);
    }

    public String store(MultipartFile file, Long formId, String fieldName) throws IOException {
        String filename = formId + "_" + fieldName + "_" + file.getOriginalFilename();
        Path destination = rootLocation.resolve(filename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return destination.toString();
    }
}
