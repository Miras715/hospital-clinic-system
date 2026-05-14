package com.example.final1.service;

import com.example.final1.repository.AmalbekMirasAppointmentRepository;
import com.example.final1.repository.AmalbekMirasDoctorRepository;
import com.example.final1.repository.AmalbekMirasMedicalRecordRepository;
import com.example.final1.repository.AmalbekMirasPatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmalbekMirasReportService {

    private final AmalbekMirasPatientRepository patientRepository;
    private final AmalbekMirasDoctorRepository doctorRepository;
    private final AmalbekMirasAppointmentRepository appointmentRepository;
    private final AmalbekMirasMedicalRecordRepository recordRepository;

    // async: generate general statistics report
    @Async
    public CompletableFuture<Map<String, Object>> generateStatisticsReport() {
        log.info("generating statistics report...");
        try {
            Thread.sleep(300);

            Map<String, Object> report = new HashMap<>();
            report.put("generatedAt", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            report.put("totalPatients", patientRepository.count());
            report.put("totalDoctors", doctorRepository.count());
            report.put("totalAppointments", appointmentRepository.count());
            report.put("totalMedicalRecords", recordRepository.count());

            log.info("statistics report generated");
            return CompletableFuture.completedFuture(report);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("report generation failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    // async: generate patient report with their appointments and records
    @Async
    public CompletableFuture<Map<String, Object>> generatePatientReport(Long patientId) {
        log.info("generating patient report for id: {}", patientId);
        try {
            Thread.sleep(400);

            Map<String, Object> report = new HashMap<>();
            report.put("generatedAt", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            report.put("patientId", patientId);
            report.put("appointmentsCount",
                    appointmentRepository.findByPatientId(patientId,
                            org.springframework.data.domain.Pageable.unpaged()).getTotalElements());
            report.put("medicalRecordsCount",
                    recordRepository.findByPatientId(patientId).size());

            log.info("patient report generated for id: {}", patientId);
            return CompletableFuture.completedFuture(report);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("patient report failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
}
