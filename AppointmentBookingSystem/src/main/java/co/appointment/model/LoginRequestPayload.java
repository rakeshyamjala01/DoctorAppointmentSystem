package co.appointment.model;

import lombok.Data;

@Data
public class LoginRequestPayload {
    String username;
    String password;
    String role;
}
