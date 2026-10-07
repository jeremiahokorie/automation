package com.automation.core.lands.service.serviceImpl;

import com.automation.config.GithubProperties;
import com.automation.core.lands.service.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CloudStorageService implements StorageService {

    private final RestClient githubClient;
    private final GithubProperties githubProperties;

    @Override
    public String store(MultipartFile file, Long formId, String fieldName) throws IOException {
        String filename = file.getOriginalFilename();
        String path = String.format("uploads/land/%d/%s_%s", formId, fieldName, filename);

        String encodedContent = Base64.getEncoder().encodeToString(file.getBytes());

        Map<String, Object> body = Map.of(
                "message", "Upload document for land application " + formId,
                "content", encodedContent,
                "branch", githubProperties.branch()
        );

        githubClient.put()
                .uri("/repos/{owner}/{repo}/contents/{path}",
                        githubProperties.owner(),
                        githubProperties.repo(),
                        path)
                .body(body)
                .retrieve()
                .toBodilessEntity();

        return String.format("https://raw.githubusercontent.com/%s/%s/%s/%s",
                githubProperties.owner(),
                githubProperties.repo(),
                githubProperties.branch(),
                path);
    }
}
