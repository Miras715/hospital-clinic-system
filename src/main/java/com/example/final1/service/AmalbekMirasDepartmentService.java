package com.example.final1.service;

import com.example.final1.dto.request.AmalbekMirasDepartmentRequest;
import com.example.final1.dto.response.AmalbekMirasDepartmentResponse;
import com.example.final1.exception.AmalbekMirasAlreadyExistsException;
import com.example.final1.exception.AmalbekMirasNotFoundException;
import com.example.final1.mapper.AmalbekMirasDepartmentMapper;
import com.example.final1.repository.AmalbekMirasDepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmalbekMirasDepartmentService {

    private final AmalbekMirasDepartmentRepository departmentRepository;
    private final AmalbekMirasDepartmentMapper departmentMapper;

    public List<AmalbekMirasDepartmentResponse> getAll() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    public AmalbekMirasDepartmentResponse getById(Long id) {
        var found = departmentRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Department not found: " + id));
        return departmentMapper.toResponse(found);
    }

    public AmalbekMirasDepartmentResponse create(AmalbekMirasDepartmentRequest request) {
        // check if name taken
        if (departmentRepository.existsByName(request.getName())) {
            throw new AmalbekMirasAlreadyExistsException("Department already exists: " + request.getName());
        }
        var saved = departmentRepository.save(departmentMapper.toEntity(request));
        log.info("saving department: {}", saved.getName());
        return departmentMapper.toResponse(saved);
    }

    public AmalbekMirasDepartmentResponse update(Long id, AmalbekMirasDepartmentRequest request) {
        var found = departmentRepository.findById(id)
                .orElseThrow(() -> new AmalbekMirasNotFoundException("Department not found: " + id));
        departmentMapper.updateEntity(request, found);
        var saved = departmentRepository.save(found);
        log.info("updated department: {}", id);
        return departmentMapper.toResponse(saved);
    }

    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new AmalbekMirasNotFoundException("Department not found: " + id);
        }
        departmentRepository.deleteById(id);
        log.info("deleted department: {}", id);
    }
}
