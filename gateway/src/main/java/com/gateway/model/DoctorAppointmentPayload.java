package com.gateway.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DoctorAppointmentPayload {
    String appointmentId;
    String status;
    LocalDate scheduledDate;
}
