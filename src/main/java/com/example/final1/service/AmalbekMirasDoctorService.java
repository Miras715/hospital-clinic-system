package com.example.final1.service;

import com.example.final1.dto.request.AmalbekMirasDoctorRequest;
import com.example.final1.dto.response.AmalbekMirasDoctorResponse;
import com.example.final1.exception.AmalbekMirasAlreadyExistsException;
import com.example.final1.exception.AmalbekMirasNotFoundException;
import com.example.final1.mapper.AmalbekMirasDoctorMapper;
import com.example.final1.repository.AmalbekMirasDepartmentRepository;
import com.example.final1.repository.AmalbekMirasDoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmalbekMirasDoctorService {

    private final AmalbekMirasDoctorRepository doctorRepository;
    private final AmalbekMirasDepartmentRepository departmentRepository;
    private final AmalbekMirasDoctorMapper doctorMapper;

    public List<AmalbekMirasDoctorResponse> getAll() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    public List<AmalbekMirasDoctorResponse> getByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId).stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    public List<AmalbekMirasDoctorResponse> getBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization).stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    public AmalbekMirasDoctorResponse getById(Long id) {
        var found = doctorRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Doctor not found: " + id));
        return doctorMapper.toResponse(found);
    }

    public AmalbekMirasDoctorResponse create(AmalbekMirasDoctorRequest request) {
        if (request.getEmail() != null && doctorRepository.existsByEmail(request.getEmail())) {
            throw new AmalbekMirasAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        var department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Department not found: " + request.getDepartmentId()));

        var doctor = doctorMapper.toEntity(request);
        doctor.setDepartment(department);

        var saved = doctorRepository.save(doctor);
        log.info("saving doctor: {} {}", saved.getFirstName(), saved.getLastName());
        return doctorMapper.toResponse(saved);
    }

    public AmalbekMirasDoctorResponse update(Long id, AmalbekMirasDoctorRequest request) {
        var found = doctorRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Doctor not found: " + id));

        var department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Department not found: " + request.getDepartmentId()));

        doctorMapper.updateEntity(request, found);
        found.setDepartment(department);

        var saved = doctorRepository.save(found);
        log.info("updated doctor: {}", id);
        return doctorMapper.toResponse(saved);
    }

    public void delete(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new AmalbekMirasNotFoundException("Doctor not found: " + id);
        }
        doctorRepository.deleteById(id);
        log.info("deleted doctor: {}", id);
    }
}
