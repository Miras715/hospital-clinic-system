package com.example.final1.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AmalbekMirasAppointmentRequest {

    @NotNull(message = "Patient is required")
    private Long patientId;

    @NotNull(message = "Doctor is required")
    private Long doctorId;

    @NotNull(message = "Date is required")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDate;

    private String notes;
}
