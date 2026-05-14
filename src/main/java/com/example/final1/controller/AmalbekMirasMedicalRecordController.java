package com.example.final1.controller;

import com.example.final1.dto.request.AmalbekMirasMedicalRecordRequest;
import com.example.final1.dto.response.AmalbekMirasMedicalRecordResponse;
import com.example.final1.service.AmalbekMirasMedicalRecordService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class AmalbekMirasMedicalRecordController {

    private final AmalbekMirasMedicalRecordService recordService;

    // GET /api/medical-records?patientId=1&doctorId=2
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<AmalbekMirasMedicalRecordResponse>> getAll(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId) {

        if (patientId != null) {
            return ResponseEntity.ok(recordService.getByPatient(patientId));
        }
        if (doctorId != null) {
            return ResponseEntity.ok(recordService.getByDoctor(doctorId));
        }
        return ResponseEntity.ok(recordService.getAll());
    }

    // GET /api/medical-records/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<AmalbekMirasMedicalRecordResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getById(id));
    }

    // POST /api/medical-records
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<AmalbekMirasMedicalRecordResponse> create(
            @Valid @RequestBody AmalbekMirasMedicalRecordRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recordService.create(request));
    }

    // PUT /api/medical-records/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<AmalbekMirasMedicalRecordResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AmalbekMirasMedicalRecordRequest request) {
        return ResponseEntity.ok(recordService.update(id, request));
    }

    // DELETE /api/medical-records/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
