package com.example.final1.service;

import com.example.final1.dto.request.AmalbekMirasAppointmentRequest;
import com.example.final1.dto.response.AmalbekMirasAppointmentResponse;
import com.example.final1.entity.AmalbekMirasAppointmentStatus;
import com.example.final1.exception.AmalbekMirasNotFoundException;
import com.example.final1.mapper.AmalbekMirasAppointmentMapper;
import com.example.final1.repository.AmalbekMirasAppointmentRepository;
import com.example.final1.repository.AmalbekMirasDoctorRepository;
import com.example.final1.repository.AmalbekMirasPatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmalbekMirasAppointmentService {

    private final AmalbekMirasAppointmentRepository appointmentRepository;
    private final AmalbekMirasPatientRepository patientRepository;
    private final AmalbekMirasDoctorRepository doctorRepository;
    private final AmalbekMirasAppointmentMapper appointmentMapper;

    // main method: pagination + filtering by status, date range, patientId, doctorId
    public Page<AmalbekMirasAppointmentResponse> getAll(
            Long patientId,
            Long doctorId,
            String status,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable) {

        if (patientId != null) {
            return appointmentRepository.findByPatientId(patientId, pageable)
                    .map(appointmentMapper::toResponse);
        }
        if (doctorId != null) {
            return appointmentRepository.findByDoctorId(doctorId, pageable)
                    .map(appointmentMapper::toResponse);
        }

        // filter by status + date range
        AmalbekMirasAppointmentStatus statusEnum = null;
        if (status != null && !status.isBlank()) {
            try {
                statusEnum = AmalbekMirasAppointmentStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("unknown status: {}", status);
            }
        }

        return appointmentRepository.findWithFilters(statusEnum, from, to, pageable)
                .map(appointmentMapper::toResponse);
    }

    public AmalbekMirasAppointmentResponse getById(Long id) {
        var found = appointmentRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Appointment not found: " + id));
        return appointmentMapper.toResponse(found);
    }

    public AmalbekMirasAppointmentResponse create(AmalbekMirasAppointmentRequest request) {
        var patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Patient not found: " + request.getPatientId()));
        var doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Doctor not found: " + request.getDoctorId()));

        var appointment = appointmentMapper.toEntity(request);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        var saved = appointmentRepository.save(appointment);
        log.info("appointment created for patient {} with doctor {}", patient.getId(), doctor.getId());
        return appointmentMapper.toResponse(saved);
    }

    public AmalbekMirasAppointmentResponse update(Long id, AmalbekMirasAppointmentRequest request) {
        var found = appointmentRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Appointment not found: " + id));

        var patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Patient not found: " + request.getPatientId()));
        var doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Doctor not found: " + request.getDoctorId()));

        appointmentMapper.updateEntity(request, found);
        found.setPatient(patient);
        found.setDoctor(doctor);

        var saved = appointmentRepository.save(found);
        log.info("updated appointment: {}", id);
        return appointmentMapper.toResponse(saved);
    }

    public AmalbekMirasAppointmentResponse updateStatus(Long id, String status) {
        var found = appointmentRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Appointment not found: " + id));

        found.setStatus(AmalbekMirasAppointmentStatus.valueOf(status.toUpperCase()));
        var saved = appointmentRepository.save(found);
        log.info("appointment {} status changed to {}", id, status);
        return appointmentMapper.toResponse(saved);
    }

    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new AmalbekMirasNotFoundException("Appointment not found: " + id);
        }
        appointmentRepository.deleteById(id);
        log.info("deleted appointment: {}", id);
    }
}
