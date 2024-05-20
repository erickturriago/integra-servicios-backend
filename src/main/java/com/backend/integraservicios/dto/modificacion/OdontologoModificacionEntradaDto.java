package com.backend.integraservicios.dto.modificacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OdontologoModificacionEntradaDto {
    @NotNull(message = "Debe proveerse el id del odontologo que se desea modificar")
    private Long id;

    @NotNull(message = "La matricula del odontólogo no puede ser nula")
    @NotBlank(message = "Debe especificarse la matricula del odontólogo")
    @Size(min = 10, message = "El campo debe tener mínimo 10 caracteres")
    private String matricula;

    @Size(max = 50, message = "El nombre del odontólogo debe tener hasta 50 caracteres")
    @NotNull(message = "El nombre de odontólogo no puede ser nulo")
    @NotBlank(message = "Debe especificarse el nombre del odontólogo")
    private String nombre;

    @Size(max = 50, message = "El apellido de odontólogo debe tener hasta 50 caracteres")
    @NotNull(message = "El apellido de odontólogo no puede ser nulo")
    @NotBlank(message = "Debe especificarse el apellido del odontólogo")
    private String apellido;
}
