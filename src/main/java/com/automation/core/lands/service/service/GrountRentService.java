package com.automation.core.lands.service.service;

import com.automation.core.lands.dto.response.GroundRentResponse;
import com.automation.core.lands.model.GroundRent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface GrountRentService {
    Map<String, String> submitform(String baNo, String landNo, String record, Double rent, MultipartFile file);

//    List<GroundRent> getAllRentService();

    List<GroundRentResponse> getCofOs();
}
