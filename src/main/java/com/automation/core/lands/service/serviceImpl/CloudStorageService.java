package com.automation.core.lands.service.serviceImpl;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class CloudStorageService {
    private final S3Client s3;
    private final String bucket = "your-bucket-name";

    public CloudStorageService() {
        this.s3 = S3Client.builder()
                .region(Region.US_EAST_1)
                .build();
    }

    public String upload(MultipartFile file, Long formId, String fieldName) throws IOException {
        String key = formId + "/" + fieldName + "/" + file.getOriginalFilename();
        s3.putObject(PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );
        return s3.utilities().getUrl(builder -> builder.bucket(bucket).key(key)).toExternalForm();
    }
}
