package com.clinic.appointmentservice.client;

import com.clinic.appointmentservice.dto.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service")
public interface DoctorClient {

    @GetMapping("/api/v1/doctors/{id}")
    DoctorResponse getDoctorbyId(@PathVariable Long id);
}
