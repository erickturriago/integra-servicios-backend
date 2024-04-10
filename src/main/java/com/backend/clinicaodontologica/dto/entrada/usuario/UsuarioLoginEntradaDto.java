package com.backend.clinicaodontologica.dto.entrada.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLoginEntradaDto {
    //@Email
    @NotBlank(message = "Debe especificarse el correo del usuario")
    private String email;
    @NotBlank(message = "Debe especificarse la contraseña del usuario")
    private String contraseña;
}
