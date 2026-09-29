package com.clinic.appointmentservice.service;

import com.clinic.appointmentservice.client.DoctorClient;
import com.clinic.appointmentservice.client.PatientClient;
import com.clinic.appointmentservice.dto.AppointmentResponse;
import com.clinic.appointmentservice.dto.CreateAppointmentRequest;
import com.clinic.appointmentservice.entity.Appointment;
import com.clinic.appointmentservice.exceptions.AppointmentAlreadyExistsException;
import com.clinic.appointmentservice.exceptions.AppointmentNotFoundException;
import com.clinic.appointmentservice.mapper.AppointmentMapper;
import com.clinic.appointmentservice.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            AppointmentMapper appointmentMapper,
            PatientClient patientClient,
            DoctorClient doctorClient
    ){
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
    }

    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest request){

        patientClient.getPatientById(request.getPatientId());
        doctorClient.getDoctorbyId(request.getDoctorId());

        if(appointmentRepository.existsByDoctorIdAndAppointmentDateTime(
                request.getDoctorId(), request.getAppointmentDateTime())){
            throw new AppointmentAlreadyExistsException(
                    request.getDoctorId(),
                    request.getAppointmentDateTime()
            );
        }
        Appointment appointment = new Appointment(
                request.getPatientId(),
                request.getDoctorId(),
                request.getAppointmentDateTime(),
                request.getStatus(),
                request.getReason()
        );
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponse(savedAppointment);
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getAppointment(Long id){

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(()-> new AppointmentNotFoundException(id));

        return appointmentMapper.toResponse(appointment);

    }
}
