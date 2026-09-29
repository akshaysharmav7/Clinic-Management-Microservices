package com.clinic.appointmentservice.dto;

public record DoctorResponse(
        Long id,
        String firstName,
        String lastName
) {
}
