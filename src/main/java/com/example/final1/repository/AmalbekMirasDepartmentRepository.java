package com.example.final1.repository;

import com.example.final1.entity.AmalbekMirasDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmalbekMirasDepartmentRepository extends JpaRepository<AmalbekMirasDepartment, Long> {

    Optional<AmalbekMirasDepartment> findByName(String name);

    boolean existsByName(String name);
}
