package co.appointment.service.intf;

import co.appointment.model.Appointment;
import co.appointment.model.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Doctor createProfile(Doctor doctor) throws Exception;
    Optional<Doctor> getProfile(String id) throws Exception;

    Appointment updateAppointment(String id, String appointmentId, String action)throws Exception;

    Doctor findByUsername(String username);

    List<Doctor> getAllDoctors();
}
