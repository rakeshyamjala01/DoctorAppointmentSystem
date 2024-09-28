package com.gateway.controller;

import com.gateway.client.ApplicationMainClient;
import com.gateway.dtos.AuthRequestDTO;
import com.gateway.dtos.JwtResponseDTO;
import com.gateway.dtos.RefreshTokenRequestDTO;
import com.gateway.model.*;
import com.gateway.payload.UserRequest;
import com.gateway.payload.UserResponse;
import com.gateway.services.JwtService;
import com.gateway.services.RefreshTokenservice;
import com.gateway.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@Slf4j
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
@RequestMapping("/api/v1")
public class SystemController {
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    ApplicationMainClient applicationMainClient;
    @Autowired
    RefreshTokenservice refreshTokenService;
    @Autowired
    AuthenticationProvider authenticateProvider;
    String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("method: getUserName(), authentication ={}",authentication);
        return  authentication.getName();
    }
    @Autowired
    private  AuthenticationManager authenticationManager;
    @PostMapping(value = "/save")
    ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
        try {
            if(userRequest.getRoles()==null){
                return ResponseEntity.badRequest().build();
            }
            UserRole role = userRequest.getRoles().stream().findFirst().get();
            String r = role.getName();
            UserResponse userResponse = userService.saveUser(userRequest);
            if(r.equals("doctor")){
                Doctor doctor = new Doctor();
                doctor.setName(userRequest.getName());
                doctor.setUsername(userRequest.getUsername());
                doctor.setSpecialization(userRequest.getSpecialization());
                doctor.setAddress(userRequest.getContact());
                applicationMainClient.createDoctorProfile(doctor);
            }else if(r.equals("patient")){
                Patient patient = new Patient();
                patient.setUsername(userRequest.getUsername());
                patient.setEmail(userRequest.getEmail());
                patient.setPhone(userRequest.getContact());
                patient.setName(userRequest.getName());
                applicationMainClient.createPatientProfile(patient);
            }
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            log.info("exception={} , method=saveUser()",e.toString());
            throw new RuntimeException(e);
        }
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity< List<UserResponse>> getAllUsers() {
        try {
            List<UserResponse> userResponses = userService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e){
            log.info("exception={}, method=getAllUsers()",e.toString());
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(){
        try{
            UserResponse userResponse = userService.getUser();
            refreshTokenService.deleteToken(userResponse.getId());
            return ResponseEntity.ok(userResponse.getId()+"");
        }catch(Exception e){
            log.info("exception={}, method=logoutUsers()",e.toString());
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        try {
            UserResponse userResponse = userService.getUser();
            return ResponseEntity.ok().body(applicationMainClient.getProfileByUsername(userResponse.getUsername()).getBody());
        } catch (Exception e){
            log.info("exception={},method :getUserprofile",e.toString());
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/test")
    public String test(@RequestHeader Map<String,String> obj) {
        try {
            return "Welcome"+getUserName();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    JwtResponseDTO getAuResponseDTO(AuthRequestDTO authRequestDTO) throws Exception{
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            // if(refreshTokenRepository.findByuserInfo(userRepository.findById(userId).get())
            // (getUserName());
            // refreshTokenService.findByUse
          try{
            User user =  userService.findByUsername(authRequestDTO.getUsername());
            if(user!=null){
                Optional<RefreshToken> token =  refreshTokenService.findByUserInfo(user);
                if(!token.isEmpty()){
                   refreshTokenService.deleteToken(user.getId());
        
                }
            }
          } catch(Exception e){

          }
            // if(refreshTokenService.findByUserInfo(userService.findByUsername(authRequestDTO.getUsername()))
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            
            try{
                return JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .token(refreshToken.getToken()).role(refreshToken.getUserInfo().getRoles().stream().findFirst().get().getName()).build();
            }catch(Exception e){
                // getAuResponseDTO(authRequestDTO);
            }
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }return new JwtResponseDTO();
    }
    
    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) throws Exception{
        return getAuResponseDTO(authRequestDTO);
    }
    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

}
