package com.automation.core.global.verification.service;

import com.automation.core.global.verification.dto.VerificationResponse;

public interface GlobalVerificationService {
    VerificationResponse verifyDocument(String documentNumber);
}
