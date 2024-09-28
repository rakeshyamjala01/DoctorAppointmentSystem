package com.gateway.controller;

import com.gateway.client.ApplicationMainClient;
import com.gateway.model.Appointment;
import com.gateway.model.Doctor;
import com.gateway.model.DoctorAppointmentPayload;
import com.gateway.model.ProfileModel;
import com.gateway.payload.UserResponse;
import com.gateway.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
@RequestMapping("/api/v1")
@Slf4j
public class GenericController {
    @Autowired
    UserService userService;
    @Autowired
    ApplicationMainClient applicationMainClient;

    @GetMapping("/appointment")
    ResponseEntity<?> getAllAppointment() {
        try {
            return ResponseEntity.ok(applicationMainClient.getOpenAppointments().getBody());
        } catch (Exception e) {
            log.warn("exception getAllAppointments {}", e.toString());
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/doctor/appointment")
    ResponseEntity<?> getDoctorAppointments(@RequestParam(value = "status", defaultValue = "all") String status) {

        try {
            UserResponse userResponse = userService.getUser();
            ProfileModel doctor = applicationMainClient.getProfileByUsername(userResponse.getUsername()).getBody();
            assert doctor != null;
            return ResponseEntity.ok(applicationMainClient.getAppointmentsForDoctor(doctor.getId(), status).getBody());
        } catch (Exception e) {
            log.warn("exception getAllAppointments {}", e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/doctor/appointment")
    ResponseEntity<?> updateDoctorAppointments(@RequestBody DoctorAppointmentPayload payload) {
        try {
            UserResponse userResponse = userService.getUser();
            ProfileModel doctor = applicationMainClient.getProfileByUsername(userResponse.getUsername()).getBody();
            assert doctor != null;
            return ResponseEntity.ok(applicationMainClient.updateDoctorAppointment(doctor.getId(), payload).getBody());
        } catch (Exception e) {
            log.warn("exception getAllAppointments {}", e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/patient/appointment")
    ResponseEntity<?> saveAppointment(@RequestBody Appointment appointment) {
        try {
            UserResponse userResponse = userService.getUser();
            ProfileModel doctor = applicationMainClient.getProfileByUsername(userResponse.getUsername()).getBody();
            assert doctor != null;
            return ResponseEntity.ok(applicationMainClient.createPatientAppointment(doctor.getId(), appointment).getBody());
        } catch (Exception e) {
            log.warn("exception getAllAppointments {}", e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/patient/appointment")
    ResponseEntity<?> getAppointment(@RequestParam(value = "status", defaultValue = "all") String status) {
        try {
            UserResponse userResponse = userService.getUser();
            ProfileModel doctor = applicationMainClient.getProfileByUsername(userResponse.getUsername()).getBody();
            assert doctor != null;
            return ResponseEntity.ok(applicationMainClient.getAppointmentsForPatient(doctor.getId(), status).getBody());
        } catch (Exception e) {
            log.warn("exception getAllAppointments {}", e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/patient/appointment/{appointmentId}")
    ResponseEntity<?> deleteAppointment(@PathVariable("appointmentId") String appointmentId) {
        try {
            return ResponseEntity.ok(applicationMainClient.deleteAppointment(appointmentId));
        } catch (Exception e) {
            log.warn("exception getAllAppointments {}", e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/patient/appointment")
    public ResponseEntity<?> getAppointmentsForPatient(@RequestBody Appointment appointment) {
        try {
            return ResponseEntity.ok(applicationMainClient.updateAppointment(appointment));
        } catch (Exception e) {
            log.warn("exception getAllAppointments {}", e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/doctorList")
    public ResponseEntity<?> getAllDoctors() {
        try {
            return ResponseEntity.ok(applicationMainClient.getAllDoctors().getBody());
        } catch (Exception e) {
            log.warn("exception getAllAppointments {}", e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }
//
//    @GetMapping("/patient/{id}")
//    ResponseEntity<?> getAllAppointment(@PathVariable("id") String patient_id, @RequestParam(name = "type", defaultValue = "all") String type) {
//
//        return ResponseEntity.ok("");
//    }
//
//    @DeleteMapping("patient/{id}")
//    ResponseEntity<?> deleteAppointment(@PathVariable("id") String patient_id, @RequestParam(name = "type", defaultValue = "all") String type) {
//
//        return ResponseEntity.ok("");
//    }
//
//    @PutMapping("patient/{id}")
//    ResponseEntity<?> patientUpdateAppointment(@PathVariable("id") String patient_id) {
//
//        return ResponseEntity.ok("");
//    }
//
//    @PostMapping("patient/{id}")
//    ResponseEntity<?> newAppointmentPatient(@PathVariable("id") String patient_id) {
//
//        return ResponseEntity.ok("");
//    }
//
//    @GetMapping("/doctor/{id}")
//    ResponseEntity<?> getAllDoctorAppointment(@PathVariable("id") String doctor_id, @RequestParam(name = "type", defaultValue = "all") String type) {
//
//        return ResponseEntity.ok("");
//
//    }
//
//    @DeleteMapping("/doctor/{id}")
//    ResponseEntity<?> deleteDoctorAppointment(@PathVariable("id") String doctor_id, @RequestParam(name = "type", defaultValue = "all") String type) {
//
//        return ResponseEntity.ok("");
//    }
//
//    @PutMapping("doctor/{id}")
//    ResponseEntity<?> putAppointment(@PathVariable("id") String doctor_id, @RequestParam(name = "type", defaultValue = "all") String type) {
//
//        return ResponseEntity.ok("");
//    }
//
//    @PostMapping("doctor/{id}")
//    ResponseEntity<?> postAppointment(@PathVariable("id") String doctor_id, @RequestParam(name = "type", defaultValue = "all") String type) {
//
//        return ResponseEntity.ok("");
//    }

}



