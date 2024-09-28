package co.appointment.service.impl;

import co.appointment.model.Appointment;
import co.appointment.model.Patient;
import co.appointment.repo.AppointmentRepository;
import co.appointment.repo.PatientRepository;
import co.appointment.service.intf.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    PatientRepository patientRepository;
    @Override
    public List<Appointment> getAllAppointments(String patientId, String type) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public Appointment createAppointment(String patientId, Appointment appointment) throws Exception {
        appointment.setPatient(this.findById(patientId));
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(String patientId, Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public String deleteAppointment(String patientId, String appointmentId) {

        return "";
    }

    @Override
    public Patient findById(String patientId) throws Exception {
        Optional<Patient> data = patientRepository.findById(patientId);
        if(data.isPresent()){
            return data.get();
        } throw new Exception();
    }

    @Override
    public Patient createOrUpdateProfile(Patient patient) throws Exception {
        return patientRepository.save(patient);
    }

    @Override
    public Patient findByUsername(String username) {
        Optional<Patient> p = patientRepository.findByUsername(username);
        return p.orElse(null);
    }
}
