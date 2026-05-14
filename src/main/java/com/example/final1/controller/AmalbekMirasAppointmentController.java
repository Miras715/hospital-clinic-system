package com.example.final1.controller;

import com.example.final1.dto.request.AmalbekMirasAppointmentRequest;
import com.example.final1.dto.response.AmalbekMirasAppointmentResponse;
import com.example.final1.service.AmalbekMirasAppointmentService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AmalbekMirasAppointmentController {

    private final AmalbekMirasAppointmentService appointmentService;

    // GET /api/appointments?page=0&size=10&sort=appointmentDate&patientId=1&doctorId=2&status=SCHEDULED&from=...&to=...
    @GetMapping
    public ResponseEntity<Page<AmalbekMirasAppointmentResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sort,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(
                appointmentService.getAll(patientId, doctorId, status, from, to, pageable)
        );
    }

    // GET /api/appointments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AmalbekMirasAppointmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    // POST /api/appointments
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    public ResponseEntity<AmalbekMirasAppointmentResponse> create(
            @Valid @RequestBody AmalbekMirasAppointmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.create(request));
    }

    // PUT /api/appointments/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<AmalbekMirasAppointmentResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AmalbekMirasAppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.update(id, request));
    }

    // PATCH /api/appointments/{id}/status?value=COMPLETED
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<AmalbekMirasAppointmentResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String value) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, value));
    }

    // DELETE /api/appointments/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
