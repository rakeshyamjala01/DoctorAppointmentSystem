package co.appointment.service.impl;
import co.appointment.model.Appointment;
import co.appointment.repo.AppointmentRepository;
import co.appointment.service.intf.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceimpl  implements AppointmentService {
    @Autowired
    private  AppointmentRepository appointmentRepository;
    @Override
    public List<Appointment> getAppointmentsForDoctor(String doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
    @Override
    public List<Appointment> getAppointmentsForPatient(String patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }
    @Override
    public void deleteAppointment(String appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }
    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAllByStatusAndPatientId(String status, String id) {
        return appointmentRepository.findAllByStatusAndPatientId(status,id);
    }

    @Override
    public List<Appointment> findAllByStatusAndDoctorId(String status, String id) {
        return appointmentRepository.findAllByStatusAndDoctorId(status,id);
    }

    @Override
    public Appointment findById(String id) throws Exception{
        Optional<Appointment> appointment  = appointmentRepository.findById(id);
        if(appointment.isPresent()){
            return appointment.get();
        }
        throw new Exception("not found");
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAllByStatus(String s) {
        return appointmentRepository.findAllByStatus(s);
    }
}
