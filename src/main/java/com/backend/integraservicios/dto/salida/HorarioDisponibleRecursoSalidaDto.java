package com.backend.integraservicios.dto.salida;

import com.backend.integraservicios.entity.Dia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HorarioDisponibleRecursoSalidaDto {

    private Long id;
    private Dia dia;
    private String horaInicio;
    private String horaFin;
}
