package com.backend.integraservicios.dto.modificacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RecursoModificacionDto {
    private Long id;

    @NotBlank(message = "El nombre del recurso no puede estar vacío")
    @Size(max = 255, message = "El nombre del recurso no puede tener más de 255 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción del recurso no puede tener más de 500 caracteres")
    private String descripcion;

    //@NotBlank(message = "La URL de la imagen no puede estar vacía")
    //private String imageUrl;

    @NotNull(message = "El ID de la unidad no puede ser nulo")
    private Long unidad;

    @NotEmpty(message = "Debe proporcionar al menos un horario disponible")
    private List<HorarioDisponibleRecursoModificacionDto> horarioDisponible;
}
