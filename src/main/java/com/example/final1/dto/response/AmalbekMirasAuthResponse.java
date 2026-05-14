package com.example.final1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AmalbekMirasAuthResponse {

    private String token;
    private String username;
    private String role;
}
