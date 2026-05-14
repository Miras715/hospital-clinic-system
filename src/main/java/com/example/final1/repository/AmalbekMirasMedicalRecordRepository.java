package com.example.final1.repository;

import com.example.final1.entity.AmalbekMirasMedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmalbekMirasMedicalRecordRepository extends JpaRepository<AmalbekMirasMedicalRecord, Long> {

    List<AmalbekMirasMedicalRecord> findByPatientId(Long patientId);

    List<AmalbekMirasMedicalRecord> findByDoctorId(Long doctorId);
}
