package com.example.final1.controller;

import com.example.final1.service.AmalbekMirasReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class AmalbekMirasReportController {

    private final AmalbekMirasReportService reportService;

    // GET /api/reports/statistics
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> getStatistics() {
        log.info("statistics report requested");
        return reportService.generateStatisticsReport()
                .thenApply(ResponseEntity::ok);
    }

    // GET /api/reports/patient/{id}
    @GetMapping("/patient/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> getPatientReport(@PathVariable Long id) {
        log.info("patient report requested for id: {}", id);
        return reportService.generatePatientReport(id)
                .thenApply(ResponseEntity::ok);
    }
}
