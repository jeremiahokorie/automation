package com.automation.core.lands.service.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface StatutoryAllocationService {
    Map<String, String> uploadDocuments(Map<String, MultipartFile> documents) throws IOException;
}
