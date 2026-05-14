package com.example.final1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AmalbekMirasLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
