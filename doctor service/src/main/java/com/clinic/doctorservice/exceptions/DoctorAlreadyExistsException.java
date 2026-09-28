package com.clinic.doctorservice.exceptions;

public class DoctorAlreadyExistsException extends RuntimeException{
    public DoctorAlreadyExistsException(String licenseNumber){
        super("Doctor already exists with license number: " + licenseNumber);
    }
}
