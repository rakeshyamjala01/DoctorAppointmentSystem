package co.appointment.controller;
import co.appointment.model.Doctor;
import co.appointment.service.intf.DoctorService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;

//@RestController
//@RequestMapping("/api/v1/doctor")
public class DoctorController {
//    @Autowired
//    DoctorService doctorService;
//    @PostMapping
//    ResponseEntity<?> createProfile(@RequestBody Doctor doctor){
//        try{
//            return ResponseEntity.ok(doctorService.createProfile(doctor));
//        }catch(Exception ex){
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//    @GetMapping
//    ResponseEntity<?> getProfile(@RequestParam("id") Long id){
//        try{
//            if(doctorService.getProfile(id).isPresent()){
//                return ResponseEntity.ok(doctorService.getProfile(id).get());
//            }
//            return ResponseEntity.badRequest().build();
//        }catch(Exception ex){
//            return ResponseEntity.internalServerError().build();
//        }
//    }

}
