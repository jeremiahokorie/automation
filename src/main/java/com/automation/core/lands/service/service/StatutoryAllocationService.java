package com.automation.core.lands.service.service;


import com.automation.core.lands.model.StatutoryAllocation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StatutoryAllocationService {
    Map<String, String> uploadDocuments(String applicantName, Map<String, MultipartFile> documents) throws IOException;

    List<StatutoryAllocation> getAllAllocations();
}
