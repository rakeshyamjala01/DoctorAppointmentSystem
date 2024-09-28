package co.appointment.controller;

import co.appointment.model.Appointment;
import co.appointment.service.intf.AppointmentService;
import co.appointment.service.intf.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
//    @Autowired
//    private AppointmentService appointmentService;
//    @Autowired
//    PatientService patientService;
//
//    /*
//    DOCTOR
//     */
//
//    @GetMapping("/doctor/{doctorId}")
//    public List<Appointment> getAppointmentsForDoctor(@PathVariable("doctorId") Long doctorId) {
//        return appointmentService.getAppointmentsForDoctor(doctorId);
//    }
//    @PostMapping("/doctor/{doctorId")
//    public Appointment updateAppointment(@PathVariable("doctorId") Long doctorId,@RequestBody Appointment appointment) {
//        return appointmentService.createAppointment(appointment);
//    }// cancel or accept the appointment
//
//    @PostMapping("/patient/{patientId}")
//    public Appointment createAppointment(@PathVariable("patientId") Long patientID,@RequestBody Appointment appointment) {
//        patientService.createAppointment(String.valueOf(patientID),appointment);
//        return appointmentService.createAppointment(appointment);
//    }
//
//
//
//
//    @GetMapping("/patient/{patientId}")
//    public List<Appointment> getAppointmentsForPatient(@PathVariable Long patientId) {
//        return appointmentService.getAppointmentsForPatient(patientId);
//    }
//
//    @DeleteMapping("/{appointmentId}")
//    public void deleteAppointment(@PathVariable Long appointmentId) {
//        appointmentService.deleteAppointment(appointmentId);
//    }
}