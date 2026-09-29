package com.clinic.appointmentservice.client;

import com.clinic.appointmentservice.dto.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service")
public interface PatientClient {
    @GetMapping("/api/v1/patients/{id}")
    PatientResponse getPatientById(@PathVariable Long id);
}
