package com.automation.util;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class FileStorageUtil {
    private final Path rootLocation = Paths.get("uploads");

    public FileStorageUtil() throws IOException {
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
    }

    public String store(byte[] content, String filename) throws IOException {
        Path destination = rootLocation.resolve(filename);
        Files.write(destination, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return "/uploads/" + filename;
    }
}
