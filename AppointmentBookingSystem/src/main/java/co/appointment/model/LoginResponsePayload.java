package co.appointment.model;

import lombok.Data;

@Data
public class LoginResponsePayload {
    String role;
    String authToken;
}
