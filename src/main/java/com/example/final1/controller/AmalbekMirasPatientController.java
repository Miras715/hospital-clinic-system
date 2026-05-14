package com.example.final1.controller;

import com.example.final1.dto.request.AmalbekMirasPatientRequest;
import com.example.final1.dto.response.AmalbekMirasPatientResponse;
import com.example.final1.service.AmalbekMirasPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class AmalbekMirasPatientController {

    private final AmalbekMirasPatientService patientService;

    // GET /api/patients?page=0&size=10&sort=lastName&keyword=John&gender=Male&bloodType=A+
    @GetMapping
    public ResponseEntity<Page<AmalbekMirasPatientResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String bloodType) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(patientService.search(keyword, gender, bloodType, pageable));
    }

    // GET /api/patients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AmalbekMirasPatientResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    // POST /api/patients
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<AmalbekMirasPatientResponse> create(@Valid @RequestBody AmalbekMirasPatientRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.create(request));
    }

    // PUT /api/patients/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<AmalbekMirasPatientResponse> update(@PathVariable Long id,
                                                               @Valid @RequestBody AmalbekMirasPatientRequest request) {
        return ResponseEntity.ok(patientService.update(id, request));
    }

    // DELETE /api/patients/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
