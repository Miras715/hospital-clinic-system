package com.example.final1.repository;

import com.example.final1.entity.AmalbekMirasAppointment;
import com.example.final1.entity.AmalbekMirasAppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AmalbekMirasAppointmentRepository extends JpaRepository<AmalbekMirasAppointment, Long> {

    Page<AmalbekMirasAppointment> findByPatientId(Long patientId, Pageable pageable);

    Page<AmalbekMirasAppointment> findByDoctorId(Long doctorId, Pageable pageable);

    Page<AmalbekMirasAppointment> findByStatus(AmalbekMirasAppointmentStatus status, Pageable pageable);

    // filter by date range and status
    @Query("SELECT a FROM AmalbekMirasAppointment a WHERE " +
           "(:status IS NULL OR a.status = :status) AND " +
           "(:from IS NULL OR a.appointmentDate >= :from) AND " +
           "(:to IS NULL OR a.appointmentDate <= :to)")
    Page<AmalbekMirasAppointment> findWithFilters(
            @Param("status") AmalbekMirasAppointmentStatus status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);
}
