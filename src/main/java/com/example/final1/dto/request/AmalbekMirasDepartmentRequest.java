package com.example.final1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AmalbekMirasDepartmentRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    private Integer floorNumber;
}
