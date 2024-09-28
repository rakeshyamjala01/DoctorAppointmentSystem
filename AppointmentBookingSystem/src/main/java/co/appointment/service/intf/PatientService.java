package co.appointment.service.intf;

import co.appointment.model.Appointment;
import co.appointment.model.Patient;

import java.util.List;

public interface PatientService {
    List<Appointment> getAllAppointments(String patientId,String type)throws Exception ;
    Appointment createAppointment(String patientId,Appointment appointment) throws Exception;
    Appointment updateAppointment(String patientId,Appointment appointment)throws Exception ;
    String deleteAppointment(String patientId,String appointmentId)throws Exception ;

    Patient findById(String patientId) throws Exception ;
    Patient createOrUpdateProfile(Patient patient)throws Exception ;
    Patient findByUsername(String username);
}
