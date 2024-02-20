package com.backend.clinicaodontologica.dto.entrada.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UsuarioLoginEntradaDto {
    //@Email
    @NotBlank(message = "Debe especificarse el correo del usuario")
    private String email;
    @NotBlank(message = "Debe especificarse la contraseña del usuario")
    private String contraseña;

    public UsuarioLoginEntradaDto(String email,String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

    public UsuarioLoginEntradaDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
