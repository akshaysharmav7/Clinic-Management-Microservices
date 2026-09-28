package com.clinic.appointmentservice.service;

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
//    private final PatientRepository patientRepository;
//    private final DoctorRepository doctorRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
//            PatientRepository patientRepository,
//            DoctorRepository doctorRepository,
            AppointmentMapper appointmentMapper
    ){
        this.appointmentRepository = appointmentRepository;
//        this.patientRepository = patientRepository;
//        this.doctorRepository = doctorRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest request){
//        Patient patient = patientRepository.findById(request.getPatientId())
//                .orElseThrow(()-> new PatientNotFoundException(request.getPatientId()));
//        Doctor doctor = doctorRepository.findById(request.getDoctorId())
//                .orElseThrow(()-> new DoctorNotFoundException(request.getDoctorId()));

        if(appointmentRepository.existsByDoctorIdAndAppointmentDateTime(
                request.getDoctorId(), request.getAppointmentDateTime())){
            throw new AppointmentAlreadyExistsException(
                    request.getDoctorId(),
                    request.getAppointmentDateTime()
            );
        }
        Appointment appointment = new Appointment(
//                patient,
//                doctor,
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
