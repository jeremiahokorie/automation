package com.automation.core.lands.service.service;

import com.automation.core.lands.model.CertificateOfOccupancy;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CertificateOfOccupancyService {
    Map<String, String> uploadDocuments(String applicantName, Map<String, MultipartFile> documents) throws IOException;

    List<CertificateOfOccupancy> getAllCertificates();
}
