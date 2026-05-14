package com.example.final1.controller;

import com.example.final1.service.AmalbekMirasFileService;
import com.example.final1.service.AmalbekMirasMedicalRecordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class AmalbekMirasFileController {

    private final AmalbekMirasFileService fileService;
    private final AmalbekMirasMedicalRecordService recordService;

    // POST /api/files/upload?recordId=1
    // upload file and attach to medical record
    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<Map<String, String>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) Long recordId) {

        String fileName = fileService.uploadFile(file);

        // attach to medical record if provided
        if (recordId != null) {
            recordService.attachFile(recordId, fileName);
        }

        log.info("file uploaded: {}", fileName);
        return ResponseEntity.ok(Map.of(
                "fileName", fileName,
                "message", "file uploaded successfully"
        ));
    }

    // GET /api/files/download/{fileName}
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) {
        Resource resource = fileService.downloadFile(fileName);

        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // DELETE /api/files/{fileName}
    @DeleteMapping("/{fileName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String fileName) {
        fileService.deleteFile(fileName);
        return ResponseEntity.ok(Map.of("message", "file deleted: " + fileName));
    }
}
