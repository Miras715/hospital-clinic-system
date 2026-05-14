package com.example.final1.controller;

import com.example.final1.dto.request.AmalbekMirasDepartmentRequest;
import com.example.final1.dto.response.AmalbekMirasDepartmentResponse;
import com.example.final1.service.AmalbekMirasDepartmentService;
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
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class AmalbekMirasDepartmentController {

    private final AmalbekMirasDepartmentService departmentService;

    // GET /api/departments
    @GetMapping
    public ResponseEntity<List<AmalbekMirasDepartmentResponse>> getAll() {
        return ResponseEntity.ok(departmentService.getAll());
    }

    // GET /api/departments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AmalbekMirasDepartmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getById(id));
    }

    // POST /api/departments
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AmalbekMirasDepartmentResponse> create(@Valid @RequestBody AmalbekMirasDepartmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.create(request));
    }

    // PUT /api/departments/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AmalbekMirasDepartmentResponse> update(@PathVariable Long id,
                                                                  @Valid @RequestBody AmalbekMirasDepartmentRequest request) {
        return ResponseEntity.ok(departmentService.update(id, request));
    }

    // DELETE /api/departments/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
