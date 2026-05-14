package com.example.final1.dto.response;

import lombok.Data;

@Data
public class AmalbekMirasDoctorResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String phone;
    private String email;
    private Long departmentId;
    private String departmentName;
}
