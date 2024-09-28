package com.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private String id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime appointmentDate;
    private String status;  // Example: "Pending", "Confirmed", "Cancelled"
    private String problemStatement;
    private String type;
}
