package com.example.final1.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AmalbekMirasPatientResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String bloodType;
}
