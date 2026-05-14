package com.example.final1.repository;

import com.example.final1.entity.AmalbekMirasDoctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AmalbekMirasDoctorRepository extends JpaRepository<AmalbekMirasDoctor, Long> {

    Optional<AmalbekMirasDoctor> findByEmail(String email);

    boolean existsByEmail(String email);

    List<AmalbekMirasDoctor> findByDepartmentId(Long departmentId);

    List<AmalbekMirasDoctor> findBySpecialization(String specialization);
}
