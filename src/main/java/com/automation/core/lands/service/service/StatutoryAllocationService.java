package com.automation.core.lands.service.service;


import com.automation.core.basepa.dto.request.ApprovalRequest;
import com.automation.core.basepa.dto.response.ApprovalResponse;
import com.automation.core.lands.dto.response.StatutoryAllocationResponse;
import com.automation.core.lands.model.StatutoryAllocation;
import com.automation.util.enums.ReportType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StatutoryAllocationService{
    Map<String, String> uploadDocuments(String applicantName, Map<String, MultipartFile> documents) throws IOException;

    List<StatutoryAllocationResponse> getAllAllocations();

    ApprovalResponse approveStatutory(Long id, ApprovalRequest commentRequest);

    ApprovalResponse rejectStatutory(Long id, ApprovalRequest commentRequest);
}
