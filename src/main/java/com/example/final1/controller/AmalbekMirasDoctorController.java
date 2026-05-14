package com.example.final1.controller;

import com.example.final1.dto.request.AmalbekMirasDoctorRequest;
import com.example.final1.dto.response.AmalbekMirasDoctorResponse;
import com.example.final1.service.AmalbekMirasDoctorService;
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
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class AmalbekMirasDoctorController {

    private final AmalbekMirasDoctorService doctorService;

    // GET /api/doctors?departmentId=1&specialization=Cardiology
    @GetMapping
    public ResponseEntity<List<AmalbekMirasDoctorResponse>> getAll(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String specialization) {

        if (departmentId != null) {
            return ResponseEntity.ok(doctorService.getByDepartment(departmentId));
        }
        if (specialization != null && !specialization.isBlank()) {
            return ResponseEntity.ok(doctorService.getBySpecialization(specialization));
        }
        return ResponseEntity.ok(doctorService.getAll());
    }

    // GET /api/doctors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AmalbekMirasDoctorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getById(id));
    }

    // POST /api/doctors
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AmalbekMirasDoctorResponse> create(@Valid @RequestBody AmalbekMirasDoctorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.create(request));
    }

    // PUT /api/doctors/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AmalbekMirasDoctorResponse> update(@PathVariable Long id,
                                                              @Valid @RequestBody AmalbekMirasDoctorRequest request) {
        return ResponseEntity.ok(doctorService.update(id, request));
    }

    // DELETE /api/doctors/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
