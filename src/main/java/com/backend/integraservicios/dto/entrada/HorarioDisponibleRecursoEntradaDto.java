package com.backend.integraservicios.dto.entrada;

import com.backend.integraservicios.entity.Dia;
import com.backend.integraservicios.entity.HorarioDisponibleRecurso;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HorarioDisponibleRecursoEntradaDto {

    private Long id;
    private Long dia;
    private String horaInicio;
    private String horaFin;
}
