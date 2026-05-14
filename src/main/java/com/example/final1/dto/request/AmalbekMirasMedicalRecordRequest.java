package com.example.final1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AmalbekMirasMedicalRecordRequest {

    @NotNull(message = "Patient is required")
    private Long patientId;

    @NotNull(message = "Doctor is required")
    private Long doctorId;

    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    private String treatment;

    private String prescription;

    private String notes;
}
