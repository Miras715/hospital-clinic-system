package com.example.final1.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AmalbekMirasDoctorRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String specialization;

    private String phone;

    @Email
    private String email;

    @NotNull(message = "Department is required")
    private Long departmentId;
}
