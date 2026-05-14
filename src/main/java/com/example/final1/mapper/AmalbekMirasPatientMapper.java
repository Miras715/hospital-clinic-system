package com.example.final1.mapper;

import com.example.final1.dto.request.AmalbekMirasPatientRequest;
import com.example.final1.dto.response.AmalbekMirasPatientResponse;
import com.example.final1.entity.AmalbekMirasPatient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AmalbekMirasPatientMapper {

    AmalbekMirasPatient toEntity(AmalbekMirasPatientRequest request);

    AmalbekMirasPatientResponse toResponse(AmalbekMirasPatient patient);

    void updateEntity(AmalbekMirasPatientRequest request, @MappingTarget AmalbekMirasPatient patient);
}
