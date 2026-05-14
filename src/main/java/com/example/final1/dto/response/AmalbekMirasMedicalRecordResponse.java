package com.example.final1.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AmalbekMirasMedicalRecordResponse {

    private Long id;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private String diagnosis;
    private String treatment;
    private String prescription;
    private LocalDate recordDate;
    private String notes;
    private String filePath;
}
