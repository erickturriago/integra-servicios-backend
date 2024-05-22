package com.backend.integraservicios.dto.modificacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModificacionEntradaDto {

    private Long id;
    private String fullname;
    private String contraseña;
    private String email;
    private int cedula;
    private LocalDate fechaRegistro;
    private int rol;
}
