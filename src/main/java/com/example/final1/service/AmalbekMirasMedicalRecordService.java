package com.example.final1.service;

import com.example.final1.dto.request.AmalbekMirasMedicalRecordRequest;
import com.example.final1.dto.response.AmalbekMirasMedicalRecordResponse;
import com.example.final1.exception.AmalbekMirasNotFoundException;
import com.example.final1.mapper.AmalbekMirasMedicalRecordMapper;
import com.example.final1.repository.AmalbekMirasDoctorRepository;
import com.example.final1.repository.AmalbekMirasMedicalRecordRepository;
import com.example.final1.repository.AmalbekMirasPatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmalbekMirasMedicalRecordService {

    private final AmalbekMirasMedicalRecordRepository recordRepository;
    private final AmalbekMirasPatientRepository patientRepository;
    private final AmalbekMirasDoctorRepository doctorRepository;
    private final AmalbekMirasMedicalRecordMapper recordMapper;

    public List<AmalbekMirasMedicalRecordResponse> getAll() {
        return recordRepository.findAll().stream()
                .map(recordMapper::toResponse)
                .toList();
    }

    public List<AmalbekMirasMedicalRecordResponse> getByPatient(Long patientId) {
        return recordRepository.findByPatientId(patientId).stream()
                .map(recordMapper::toResponse)
                .toList();
    }

    public List<AmalbekMirasMedicalRecordResponse> getByDoctor(Long doctorId) {
        return recordRepository.findByDoctorId(doctorId).stream()
                .map(recordMapper::toResponse)
                .toList();
    }

    public AmalbekMirasMedicalRecordResponse getById(Long id) {
        var found = recordRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Medical record not found: " + id));
        return recordMapper.toResponse(found);
    }

    public AmalbekMirasMedicalRecordResponse create(AmalbekMirasMedicalRecordRequest request) {
        var patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Patient not found: " + request.getPatientId()));
        var doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Doctor not found: " + request.getDoctorId()));

        var record = recordMapper.toEntity(request);
        record.setPatient(patient);
        record.setDoctor(doctor);

        var saved = recordRepository.save(record);
        log.info("medical record created for patient: {}", patient.getId());
        return recordMapper.toResponse(saved);
    }

    public AmalbekMirasMedicalRecordResponse update(Long id, AmalbekMirasMedicalRecordRequest request) {
        var found = recordRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Medical record not found: " + id));

        var patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Patient not found: " + request.getPatientId()));
        var doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Doctor not found: " + request.getDoctorId()));

        recordMapper.updateEntity(request, found);
        found.setPatient(patient);
        found.setDoctor(doctor);

        var saved = recordRepository.save(found);
        log.info("updated medical record: {}", id);
        return recordMapper.toResponse(saved);
    }

    // update file path after upload
    public void attachFile(Long id, String filePath) {
        var found = recordRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Medical record not found: " + id));
        found.setFilePath(filePath);
        recordRepository.save(found);
        log.info("file attached to record: {}", id);
    }

    public void delete(Long id) {
        if (!recordRepository.existsById(id)) {
            throw new AmalbekMirasNotFoundException("Medical record not found: " + id);
        }
        recordRepository.deleteById(id);
        log.info("deleted medical record: {}", id);
    }
}
