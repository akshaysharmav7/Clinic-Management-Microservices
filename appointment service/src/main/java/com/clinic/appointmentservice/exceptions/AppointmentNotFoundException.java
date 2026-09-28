package com.clinic.appointmentservice.exceptions;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(Long id){
        super("Appointment not found with id: " + id);
    }
}
