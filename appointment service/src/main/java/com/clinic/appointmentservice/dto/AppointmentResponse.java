package com.clinic.appointmentservice.dto;

import com.clinic.appointmentservice.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentResponse {

    private final Long id;
    private final Long patientId;
    private final Long doctorId;
    private final LocalDateTime appointmentDateTime;
    private final AppointmentStatus status;
    private final String reason;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public AppointmentResponse(
            Long id,
            Long patientId,
            Long doctorId,
            LocalDateTime appointmentDateTime,
            AppointmentStatus status,
            String reason,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
        this.reason = reason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }
    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}