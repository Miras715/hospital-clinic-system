package com.example.final1.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AmalbekMirasAppointmentResponse {

    private Long id;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private LocalDateTime appointmentDate;
    private String status;
    private String notes;
    private LocalDateTime createdAt;
}
