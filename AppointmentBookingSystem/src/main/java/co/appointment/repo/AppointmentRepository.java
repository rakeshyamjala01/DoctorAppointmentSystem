package co.appointment.repo;

import co.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    List<Appointment> findByDoctorId(String doctorId);
    List<Appointment> findByPatientId(String patientId);
    List<Appointment>  findAllByStatusAndDoctorId(String status,String id);
    List<Appointment>  findAllByStatusAndPatientId(String status,String id);

    List<Appointment> findAllByStatus(String s);
}