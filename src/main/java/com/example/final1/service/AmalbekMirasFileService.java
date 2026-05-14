package com.example.final1.service;

import com.example.final1.exception.AmalbekMirasNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class AmalbekMirasFileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadPath);
            log.info("upload dir ready: {}", uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("could not create upload dir", e);
        }
    }

    public String uploadFile(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            throw new RuntimeException("invalid file name");
        }

        // generate unique name to avoid collisions
        String extension = "";
        int dotIndex = originalName.lastIndexOf(".");
        if (dotIndex > 0) {
            extension = originalName.substring(dotIndex);
        }
        String fileName = UUID.randomUUID() + extension;

        try {
            Path targetPath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            log.info("file uploaded: {}", fileName);
            return fileName;
        } catch (IOException e) {
            log.error("failed to upload file: {}", e.getMessage());
            throw new RuntimeException("could not save file", e);
        }
    }

    public Resource downloadFile(String fileName) {
        try {
            Path filePath = uploadPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new AmalbekMirasNotFoundException("File not found: " + fileName);
            }
            log.info("downloading file: {}", fileName);
            return resource;
        } catch (MalformedURLException e) {
            throw new AmalbekMirasNotFoundException("File not found: " + fileName);
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = uploadPath.resolve(fileName).normalize();
            boolean deleted = Files.deleteIfExists(filePath);
            if (deleted) {
                log.info("file deleted: {}", fileName);
            }
        } catch (IOException e) {
            log.error("could not delete file: {}", e.getMessage());
        }
    }
}
