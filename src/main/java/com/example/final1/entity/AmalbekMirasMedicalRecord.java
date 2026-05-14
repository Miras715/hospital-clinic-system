package com.example.final1.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "medical_records")
public class AmalbekMirasMedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private AmalbekMirasPatient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private AmalbekMirasDoctor doctor;

    @Column(nullable = false)
    private String diagnosis;

    private String treatment;

    private String prescription;

    @Column(name = "record_date")
    private LocalDate recordDate = LocalDate.now();

    private String notes;

    // path to uploaded file
    @Column(name = "file_path")
    private String filePath;
}
