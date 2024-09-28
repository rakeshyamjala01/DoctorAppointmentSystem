package co.appointment.controller;

import co.appointment.model.*;
import co.appointment.repo.UserRepository;
import co.appointment.service.intf.AppointmentService;
import co.appointment.service.intf.DoctorService;
import co.appointment.service.intf.PatientService;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
@Slf4j
public class GeneralController {
    @Autowired
    PatientService patientService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    UserRepository userRepository;

    User saveUser(User user) {
        return userRepository.save(user);
    }
    @GetMapping("/profile/{username}")
    ResponseEntity<?> getProfileByUsername(@PathVariable("username") String username){
        try{
          Patient patient =   patientService.findByUsername(username);
          Doctor  doctor = doctorService.findByUsername(username);
          if(patient!=null){
              return ResponseEntity.ok(patient);
          }else if(doctor!=null){
              return ResponseEntity.ok(doctor);
          } else{
              return ResponseEntity.badRequest().build();
          }
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/appointment")
    ResponseEntity<?> getOpenAppointments() {
        try {
            List<Appointment> data = appointmentService.findAllByStatus("pending");
            if (data != null) {
                return ResponseEntity.ok(data);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            log.warn("ex : {}", ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/doctor")
    ResponseEntity<?> createDoctorProfile(@RequestBody Doctor doctor) {
        try {
            doctor = doctorService.createProfile(doctor);
            if (doctor != null) {
                return ResponseEntity.ok(doctorService.createProfile(doctor));
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            log.warn("ex : {}", ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/doctor")
    ResponseEntity<?> updateDoctorProfile(@RequestBody Doctor doctor) {
        try {
            return ResponseEntity.ok(doctorService.createProfile(doctor));
        } catch (Exception ex) {
            log.warn("ex : {}", ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/doctor/{id}")
    ResponseEntity<?> getProfile(@PathVariable("id") String id) {
        try {
            if (doctorService.getProfile(id).isPresent()) {
                return ResponseEntity.ok(doctorService.getProfile(id).get());
            }

            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
    /*
    @JavaDoc
    quick update status of appointment
     */
    @PostMapping("/doctor/{id}/appointment/{appointmentId}")
    ResponseEntity<?> updateAppointmentCancelAccept(@PathVariable("id") String id, @PathVariable("appointmentId") String appointmentId, @RequestParam(name = "action", defaultValue = "accept") String action) {
        try {
            Appointment appointment = doctorService.updateAppointment(id, appointmentId, action);
            if (appointment != null) {
                return ResponseEntity.ok(appointment);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /*
    patient
     */
    @GetMapping("/patient/{id}")
    public ResponseEntity<?> getAppointmentsForPatient(@PathVariable("id") String id) throws Exception {
        try {
            Patient patient = patientService.findById(String.valueOf(id));
            if (patient != null) {
                return ResponseEntity.ok(patient);
            }
            return ResponseEntity.badRequest().build();

        } catch (Exception ex) {
            log.warn("unable to find patient{} ,{}:", id, ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/patient")
    ResponseEntity<?> createPatientProfile(@RequestBody Patient patient) {
        try {
            log.info("received patient {}", patient);
            return ResponseEntity.ok(patientService.createOrUpdateProfile(patient));
        } catch (Exception ex) {
            log.warn("error to create patient{} ,{}:", patient, ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/patient/{id}/appointment")
    ResponseEntity<?> createPatientAppointment(@PathVariable("id") String id, @RequestBody Appointment appointment) {
        try {
            log.info("received Appointment {}", appointment);
            appointment.setStatus("pending");
            appointment = patientService.createAppointment(id, appointment);
            if (appointment != null) {
                return ResponseEntity.ok(patientService.createAppointment(id, appointment));
            }
            return ResponseEntity.badRequest().build();

        } catch (Exception ex) {
            log.warn("error to create Appointment{} ,{}:", appointment, ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/patient/{id}/appointment")
    public ResponseEntity<?> getAppointmentsForPatient(@PathVariable("id") String id, @RequestParam(value = "status", defaultValue = "all") String status) {
        try {
            List<Appointment> data = new ArrayList<>();
            if (status.equals("all")) {
                log.info("patient {}", id);
                data = appointmentService.getAppointmentsForPatient(id);
            } else {
                data = appointmentService.findAllByStatusAndPatientId(status, id);
            }
            return ResponseEntity.ok(data);

        } catch (Exception ex) {
            log.warn("error to get Appointments {} ", ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping("/patient/{appointmentId}/appointment")
    public ResponseEntity<?> deleteAppointment(@PathVariable("appointmentId") String id) {
      try{
          appointmentService.deleteAppointment(id);
          return ResponseEntity.ok().build();
      } catch (Exception ex) {
          log.warn("error to delete Appointments {} ", ex.toString());
          return ResponseEntity.internalServerError().build();
      }
    }

    @PostMapping("/doctor/{id}/appointment")
    public ResponseEntity<?> getAppointmentsForPatient(@PathVariable("id") String id, @RequestBody DoctorAppointmentPayload doctorAppointmentPayload) {
        try {
            Optional<Doctor> doctor = doctorService.getProfile(id);
            if (doctorAppointmentPayload.getAppointmentId() != null & doctor.isPresent()) {
                Appointment appointment = appointmentService.findById(doctorAppointmentPayload.getAppointmentId());
                appointment.setDoctor(doctor.get());
                appointment.setStatus(doctorAppointmentPayload.getStatus());
                appointment.setAppointmentDate(doctorAppointmentPayload.getScheduledDate().atStartOfDay());
                Appointment a = appointmentService.updateAppointment(appointment);
                log.debug("successfully updated {} ", a);
                return ResponseEntity.ok(a);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            log.warn("error to get Appointments {} ", ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }
    @PutMapping("/patient/appointment")
    public ResponseEntity<?> getAppointmentsForPatient(@RequestBody Appointment appointment) {
    try{
        return ResponseEntity.ok(appointmentService.updateAppointment(appointment));
    }catch(Exception ex){
        return ResponseEntity.internalServerError().build();
    }
    }

        @GetMapping("/doctor/{id}/appointment")
    public ResponseEntity<?> getAppointmentsForDoctor(@PathVariable("id") String id, @RequestParam(value = "status", defaultValue = "all") String status) {
        try {
            List<Appointment> data = new ArrayList<>();
            if (status.equals("all")) {
                log.info("doctor Id :  {}", id);
                data = appointmentService.getAppointmentsForDoctor(id);
            } else {
                data = appointmentService.findAllByStatusAndDoctorId(status, id);
            }
            return ResponseEntity.ok(data);

        } catch (Exception ex) {
            log.warn("error to get Appointments {} ", ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/doctorList")
    public ResponseEntity<?> getAllDoctors(){
        try{
            return ResponseEntity.ok( doctorService.getAllDoctors());
        }catch (Exception ex) {
            log.warn("error to get Appointments {} ", ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }
}
