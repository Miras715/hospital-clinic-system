package com.example.final1.mapper;

import com.example.final1.dto.request.AmalbekMirasDepartmentRequest;
import com.example.final1.dto.response.AmalbekMirasDepartmentResponse;
import com.example.final1.entity.AmalbekMirasDepartment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AmalbekMirasDepartmentMapper {

    AmalbekMirasDepartment toEntity(AmalbekMirasDepartmentRequest request);

    AmalbekMirasDepartmentResponse toResponse(AmalbekMirasDepartment department);

    void updateEntity(AmalbekMirasDepartmentRequest request, @MappingTarget AmalbekMirasDepartment department);
}
