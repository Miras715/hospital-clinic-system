package com.example.final1.service;

import com.example.final1.entity.AmalbekMirasAppointment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AmalbekMirasEmailNotificationService {

    // async: send appointment confirmation to patient
    @Async
    public CompletableFuture<String> sendAppointmentConfirmation(AmalbekMirasAppointment appointment) {
        log.info("sending appointment confirmation to: {}", appointment.getPatient().getEmail());
        try {
            // simulate email sending delay
            Thread.sleep(500);

            String message = String.format(
                    "Dear %s %s, your appointment with Dr. %s %s is confirmed for %s",
                    appointment.getPatient().getFirstName(),
                    appointment.getPatient().getLastName(),
                    appointment.getDoctor().getFirstName(),
                    appointment.getDoctor().getLastName(),
                    appointment.getAppointmentDate()
            );

            log.info("appointment confirmation sent to: {}", appointment.getPatient().getEmail());
            return CompletableFuture.completedFuture(message);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("failed to send confirmation email: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    // async: send cancellation notice
    @Async
    public CompletableFuture<String> sendCancellationNotice(AmalbekMirasAppointment appointment) {
        log.info("sending cancellation notice to: {}", appointment.getPatient().getEmail());
        try {
            Thread.sleep(300);

            String message = String.format(
                    "Dear %s %s, your appointment on %s has been cancelled.",
                    appointment.getPatient().getFirstName(),
                    appointment.getPatient().getLastName(),
                    appointment.getAppointmentDate()
            );

            log.info("cancellation notice sent to: {}", appointment.getPatient().getEmail());
            return CompletableFuture.completedFuture(message);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("failed to send cancellation email: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    // async: send welcome email after registration
    @Async
    public CompletableFuture<String> sendWelcomeEmail(String email, String username) {
        log.info("sending welcome email to: {}", email);
        try {
            Thread.sleep(200);

            String message = String.format(
                    "Welcome to Hospital System, %s! Your account has been created.", username);

            log.info("welcome email sent to: {}", email);
            return CompletableFuture.completedFuture(message);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("failed to send welcome email: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
}
