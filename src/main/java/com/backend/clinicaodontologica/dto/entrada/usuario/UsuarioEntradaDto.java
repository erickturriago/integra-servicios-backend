package com.backend.clinicaodontologica.dto.entrada.usuario;


import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public class UsuarioEntradaDto {

    @NotNull(message = "El nombre del paciente no puede ser nulo")
    @NotBlank(message = "Debe especificarse el nombre del paciente")
    @Size(max = 50, message = "El nombre debe tener hasta 50 caracteres")
    private String nombre;

    @Size(max = 50, message = "El apellido debe tener hasta 50 caracteres")
    @NotBlank(message = "Debe especificarse el apellido del paciente")
    private String apellido;
    @NotBlank(message = "Debe especificarse la contraseña del usuario")
    private String contraseña;
    //@Email
    @NotBlank(message = "Debe especificarse el correo del usuario")
    private String email;
    @NotNull(message = "La cedula del usuario no puede ser nulo")
    private Integer cedula;
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha de registro del usuario")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaRegistro;
    //private Set<String> roles;

    public UsuarioEntradaDto(String nombre, String apellido, String contraseña, String email, int cedula, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.email = email;
        this.cedula = cedula;
        this.fechaRegistro = fechaRegistro;
    }

    public UsuarioEntradaDto(String email,String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

    public UsuarioEntradaDto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
