package com.clinic.appointmentservice.service;

import com.clinic.appointmentservice.client.PatientClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class PatientValidationService {

    private final PatientClient patientClient;

    public PatientValidationService(PatientClient patientClient){
        this.patientClient = patientClient;
    }

    @CircuitBreaker(name = "patientService", fallbackMethod = "patientServiceFallback")
    public void validatePatient(Long patientId){
        patientClient.getPatientById(patientId);
    }

    private void patientServiceFallback(Long patientId, Exception exception){
        throw new RuntimeException("Patient Service is currently unavailable");
    }
}
