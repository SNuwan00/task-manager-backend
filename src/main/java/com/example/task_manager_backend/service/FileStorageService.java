package com.example.task_manager_backend.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    private final String uploadDir;

    public FileStorageService(Dotenv dotenv) {
        this.uploadDir = dotenv.get("UPLOAD_DIR");
    }

    public String storeFile(MultipartFile file) throws IOException {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, file.getBytes());
        return filePath.toString();
    }

    public void deleteFile(String filePath) throws IOException {
        if (filePath != null && !filePath.isEmpty()) {
            Files.deleteIfExists(Paths.get(filePath));
        }
    }
}