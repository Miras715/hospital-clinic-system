package com.example.final1.mapper;

import com.example.final1.dto.request.AmalbekMirasMedicalRecordRequest;
import com.example.final1.dto.response.AmalbekMirasMedicalRecordResponse;
import com.example.final1.entity.AmalbekMirasMedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AmalbekMirasMedicalRecordMapper {

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "recordDate", ignore = true)
    @Mapping(target = "filePath", ignore = true)
    AmalbekMirasMedicalRecord toEntity(AmalbekMirasMedicalRecordRequest request);

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(target = "patientName", expression = "java(record.getPatient().getFirstName() + \" \" + record.getPatient().getLastName())")
    @Mapping(target = "doctorName", expression = "java(record.getDoctor().getFirstName() + \" \" + record.getDoctor().getLastName())")
    AmalbekMirasMedicalRecordResponse toResponse(AmalbekMirasMedicalRecord record);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "recordDate", ignore = true)
    @Mapping(target = "filePath", ignore = true)
    void updateEntity(AmalbekMirasMedicalRecordRequest request, @MappingTarget AmalbekMirasMedicalRecord record);
}
