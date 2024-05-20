package com.backend.integraservicios.dto.modificacion;

import com.backend.integraservicios.entity.Dia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HorarioDisponibleRecursoModificacionDto {
    private Long id;
    private Long dia;
    private String horaInicio;
    private String horaFin;
}
