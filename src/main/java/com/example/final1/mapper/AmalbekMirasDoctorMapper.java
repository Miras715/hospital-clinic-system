package com.example.final1.mapper;

import com.example.final1.dto.request.AmalbekMirasDoctorRequest;
import com.example.final1.dto.response.AmalbekMirasDoctorResponse;
import com.example.final1.entity.AmalbekMirasDoctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AmalbekMirasDoctorMapper {

    @Mapping(target = "department", ignore = true)
    AmalbekMirasDoctor toEntity(AmalbekMirasDoctorRequest request);

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    AmalbekMirasDoctorResponse toResponse(AmalbekMirasDoctor doctor);

    @Mapping(target = "department", ignore = true)
    void updateEntity(AmalbekMirasDoctorRequest request, @MappingTarget AmalbekMirasDoctor doctor);
}
