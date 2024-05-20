package com.backend.integraservicios.security;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String contraseña;
    private String role;
    private int cedula;
}
