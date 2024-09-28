package com.gateway.client;

import com.gateway.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "application-main")
public interface ApplicationMainClient {
     String uri = "/api/v1/";

     @GetMapping(uri+"/profile/{username}")
     ResponseEntity<ProfileModel> getProfileByUsername(@PathVariable("username") String username);
    @GetMapping(uri+"appointment")
    ResponseEntity<?> getOpenAppointments();
    @PostMapping(uri+"/doctor")
    ResponseEntity<?> createDoctorProfile(@RequestBody Doctor doctor);
    @PutMapping(uri+"/doctor")
    ResponseEntity<?> updateDoctorProfile(@RequestBody Doctor doctor);
    @GetMapping(uri+"/doctor/{id}")
    ResponseEntity<?> getProfile(@PathVariable("id") String id) ;
    @PostMapping(uri+"/doctor/{id}/appointment/{appointmentId}")
    ResponseEntity<?> updateAppointmentCancelAccept(@PathVariable("id") String id, @PathVariable("appointmentId") String appointmentId, @RequestParam(name = "action", defaultValue = "accept") String action);
    @GetMapping(uri+"/patient/{id}")
    ResponseEntity<?> getAppointmentsForPatient(@PathVariable("id") String id);
    @PostMapping(uri+"/patient")
    ResponseEntity<?> createPatientProfile(@RequestBody Patient patient) ;
    @PostMapping(uri+"/patient/{id}/appointment")
    ResponseEntity<?> createPatientAppointment(@PathVariable("id") String id, @RequestBody Appointment appointment);
    @GetMapping(uri+"/patient/{id}/appointment")
    ResponseEntity<?> getAppointmentsForPatient(@PathVariable("id") String id, @RequestParam(value = "status", defaultValue = "all") String status);
    @PostMapping(uri+"/doctor/{id}/appointment")
    ResponseEntity<?> updateDoctorAppointment(@PathVariable("id") String id, @RequestBody DoctorAppointmentPayload doctorAppointmentPayload);
    @GetMapping(uri+"/doctor/{id}/appointment")
    ResponseEntity<?> getAppointmentsForDoctor(@PathVariable("id") String id, @RequestParam(value = "status", defaultValue = "all") String status) ;
    @DeleteMapping(uri+"/patient/{appointmentId}/appointment")
    ResponseEntity<?> deleteAppointment(@PathVariable("appointmentId") String id) ;
    @PutMapping(uri+"/patient/appointment")
    public ResponseEntity<?> updateAppointment(@RequestBody Appointment appointment);
    @GetMapping(uri+"/doctorList")
    public ResponseEntity<?> getAllDoctors();
}
