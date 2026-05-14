package com.example.final1.repository;

import com.example.final1.entity.AmalbekMirasPatient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AmalbekMirasPatientRepository extends JpaRepository<AmalbekMirasPatient, Long> {

    Optional<AmalbekMirasPatient> findByEmail(String email);

    boolean existsByEmail(String email);

    // search by first or last name
    @Query("SELECT p FROM AmalbekMirasPatient p WHERE " +
           "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<AmalbekMirasPatient> searchByName(@Param("keyword") String keyword, Pageable pageable);

    Page<AmalbekMirasPatient> findByGender(String gender, Pageable pageable);

    Page<AmalbekMirasPatient> findByBloodType(String bloodType, Pageable pageable);
}
