package com.example.final1.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class AmalbekMirasGlobalExceptionHandler {

    @ExceptionHandler(AmalbekMirasNotFoundException.class)
    public ResponseEntity<AmalbekMirasErrorResponse> handleNotFound(AmalbekMirasNotFoundException ex) {
        log.warn("Not found: {}", ex.getMessage());
        var error = new AmalbekMirasErrorResponse(404, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AmalbekMirasAlreadyExistsException.class)
    public ResponseEntity<AmalbekMirasErrorResponse> handleAlreadyExists(AmalbekMirasAlreadyExistsException ex) {
        log.warn("Already exists: {}", ex.getMessage());
        var error = new AmalbekMirasErrorResponse(409, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AmalbekMirasErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        var error = new AmalbekMirasErrorResponse(401, "Invalid username or password", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AmalbekMirasErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        var error = new AmalbekMirasErrorResponse(403, "Access denied", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    // catch everything else
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AmalbekMirasErrorResponse> handleGeneral(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        var error = new AmalbekMirasErrorResponse(500, "Internal server error", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
