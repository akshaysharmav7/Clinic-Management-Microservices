package com.clinic.appointmentservice.dto;

public record PatientResponse(
        Long id,
        String firstName,
        String LastName
) {
}
