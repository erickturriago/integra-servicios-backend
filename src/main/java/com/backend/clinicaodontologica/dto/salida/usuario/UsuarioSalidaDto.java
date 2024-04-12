package com.backend.clinicaodontologica.dto.salida.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSalidaDto {
    private int id;
    private String nombre;
    private String apellido;
    private String contraseña;
    private String email;
    private Integer cedula;
    private String fechaRegistro;
    private int rol;
}
