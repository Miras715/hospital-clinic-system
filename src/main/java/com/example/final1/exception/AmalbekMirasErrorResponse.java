package com.example.final1.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AmalbekMirasErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;
}
