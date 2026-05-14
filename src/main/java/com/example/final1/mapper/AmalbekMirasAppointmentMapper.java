package com.example.final1.mapper;

import com.example.final1.dto.request.AmalbekMirasAppointmentRequest;
import com.example.final1.dto.response.AmalbekMirasAppointmentResponse;
import com.example.final1.entity.AmalbekMirasAppointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AmalbekMirasAppointmentMapper {

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AmalbekMirasAppointment toEntity(AmalbekMirasAppointmentRequest request);

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(target = "patientName", expression = "java(appointment.getPatient().getFirstName() + \" \" + appointment.getPatient().getLastName())")
    @Mapping(target = "doctorName", expression = "java(appointment.getDoctor().getFirstName() + \" \" + appointment.getDoctor().getLastName())")
    @Mapping(source = "status", target = "status")
    AmalbekMirasAppointmentResponse toResponse(AmalbekMirasAppointment appointment);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(AmalbekMirasAppointmentRequest request, @MappingTarget AmalbekMirasAppointment appointment);
}
