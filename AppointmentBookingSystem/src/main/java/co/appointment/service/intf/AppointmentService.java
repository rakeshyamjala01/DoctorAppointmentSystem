package co.appointment.service.intf;

import co.appointment.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AppointmentService {
    public List<Appointment> getAppointmentsForDoctor(String doctorId);
    public List<Appointment> getAppointmentsForPatient(String patientId) ;
    public void deleteAppointment(String appointmentId) ;
    public Appointment createAppointment(Appointment appointment)  throws Exception;

    List<Appointment> findAllByStatusAndPatientId(String status, String id);
    List<Appointment> findAllByStatusAndDoctorId(String status, String id);
    Appointment findById(String id)throws Exception;

    Appointment updateAppointment(Appointment appointment);

    List<Appointment> findAllByStatus(String s);
}
