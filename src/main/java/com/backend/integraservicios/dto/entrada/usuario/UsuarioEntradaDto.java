package com.backend.integraservicios.dto.entrada.usuario;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private int rol;

}
