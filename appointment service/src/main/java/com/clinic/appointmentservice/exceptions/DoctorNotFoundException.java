package com.clinic.appointmentservice.exceptions;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(Long doctorId){
        super("Doctor not found with id: " + doctorId);
    }
}
