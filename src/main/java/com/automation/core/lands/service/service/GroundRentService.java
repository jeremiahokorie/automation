package com.automation.core.lands.service.service;

import com.automation.core.basepa.dto.request.ApprovalRequest;
import com.automation.core.basepa.dto.response.ApprovalResponse;
import com.automation.core.lands.dto.response.GroundRentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface GroundRentService {
    Map<String, String> submitform(String baNo, String landNo, String record, Double rent, MultipartFile file);

//    List<GroundRent> getAllRentService();

    List<GroundRentResponse> getCofOs();

    ApprovalResponse approveGroundRent(Long id, ApprovalRequest commentRequest);

    ApprovalResponse rejectGrounRent(Long id, ApprovalRequest commentRequest);
}
