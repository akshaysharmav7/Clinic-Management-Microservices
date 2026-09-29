package com.clinic.appointmentservice.client;

import com.clinic.appointmentservice.exceptions.DoctorNotFoundException;
import com.clinic.appointmentservice.exceptions.PatientNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 404) {

            if (methodKey.contains("PatientClient")) {
                return new PatientNotFoundException(
                        extractId(methodKey)
                );
            }

            if (methodKey.contains("DoctorClient")) {
                return new DoctorNotFoundException(
                        extractId(methodKey)
                );
            }
        }

        return new RuntimeException(
                "Remote service call failed with status: "
                        + response.status()
        );
    }

    private Long extractId(String methodKey) {
        return null;
    }
}