package com.example.final1.service;

import com.example.final1.dto.request.AmalbekMirasPatientRequest;
import com.example.final1.dto.response.AmalbekMirasPatientResponse;
import com.example.final1.exception.AmalbekMirasAlreadyExistsException;
import com.example.final1.exception.AmalbekMirasNotFoundException;
import com.example.final1.mapper.AmalbekMirasPatientMapper;
import com.example.final1.repository.AmalbekMirasPatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmalbekMirasPatientService {

    private final AmalbekMirasPatientRepository patientRepository;
    private final AmalbekMirasPatientMapper patientMapper;

    public Page<AmalbekMirasPatientResponse> getAll(Pageable pageable) {
        return patientRepository.findAll(pageable).map(patientMapper::toResponse);
    }

    // search + filter in one method
    public Page<AmalbekMirasPatientResponse> search(String keyword, String gender,
                                                     String bloodType, Pageable pageable) {
        if (keyword != null && !keyword.isBlank()) {
            return patientRepository.searchByName(keyword, pageable).map(patientMapper::toResponse);
        }
        if (gender != null && !gender.isBlank()) {
            return patientRepository.findByGender(gender, pageable).map(patientMapper::toResponse);
        }
        if (bloodType != null && !bloodType.isBlank()) {
            return patientRepository.findByBloodType(bloodType, pageable).map(patientMapper::toResponse);
        }
        return patientRepository.findAll(pageable).map(patientMapper::toResponse);
    }

    public AmalbekMirasPatientResponse getById(Long id) {
        var found = patientRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Patient not found: " + id));
        return patientMapper.toResponse(found);
    }

    public AmalbekMirasPatientResponse create(AmalbekMirasPatientRequest request) {
        // check if email taken
        if (request.getEmail() != null && patientRepository.existsByEmail(request.getEmail())) {
            throw new AmalbekMirasAlreadyExistsException("Email already exists: " + request.getEmail());
        }
        var patient = patientMapper.toEntity(request);
        var saved = patientRepository.save(patient);
        log.info("saving patient: {} {}", saved.getFirstName(), saved.getLastName());
        return patientMapper.toResponse(saved);
    }

    public AmalbekMirasPatientResponse update(Long id, AmalbekMirasPatientRequest request) {
        var found = patientRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Patient not found: " + id));
        patientMapper.updateEntity(request, found);
        var saved = patientRepository.save(found);
        log.info("updated patient: {}", id);
        return patientMapper.toResponse(saved);
    }

    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new AmalbekMirasNotFoundException("Patient not found: " + id);
        }
        patientRepository.deleteById(id);
        log.info("deleted patient: {}", id);
    }
}
