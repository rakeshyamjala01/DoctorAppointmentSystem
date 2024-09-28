package co.appointment.service.impl;

import co.appointment.model.Appointment;
import co.appointment.model.Doctor;
import co.appointment.repo.AppointmentRepository;
import co.appointment.repo.DoctorRepository;
import co.appointment.service.intf.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    AppointmentRepository appointmentRepository;
    @Override
    public Doctor createProfile(Doctor doctor) throws Exception{
        return doctorRepository.save(doctor);
    }

    @Override
    public Optional<Doctor> getProfile(String id)  throws Exception{
        return doctorRepository.findById(id);
    }

    @Override
    public Appointment updateAppointment(String id, String appointmentId, String action) throws Exception {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        Optional<Doctor> doctor = this.getProfile(id);
        if(doctor.isPresent()&appointment.isPresent()){
            Appointment app = appointment.get();
            app.setDoctor(doctor.get());
            app.setStatus(action);
            appointmentRepository.save(app);
        }throw new Exception("invalid inputs");
    }

    @Override
    public Doctor findByUsername(String username) {
        Optional<Doctor> p = doctorRepository.findByUsername(username);
        return p.orElse(null);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
