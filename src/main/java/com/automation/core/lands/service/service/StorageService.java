package com.automation.core.lands.service.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface StorageService {
    /**
     * Stores a file in the cloud/local storage.
     * @param file The file to upload.
     * @param formId The ID of the associated form.
     * @param fieldName The name of the field (e.g., "passportPhoto").
     * @return The URL or path to the stored file.
     * @throws IOException If an I/O error occurs during upload.
     */
    String store(MultipartFile file, Long formId, String fieldName) throws IOException;
}
