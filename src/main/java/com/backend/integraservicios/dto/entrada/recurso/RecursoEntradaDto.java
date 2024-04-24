package com.backend.integraservicios.dto.entrada.recurso;

import com.backend.integraservicios.dto.entrada.horarioDisponbielRecurso.HorarioDisponibleRecursoEntradaDto;
import com.backend.integraservicios.entity.HorarioDisponibleRecurso;
import com.backend.integraservicios.entity.Unidad;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RecursoEntradaDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imageUrl;
    private Long unidad;
    private List<HorarioDisponibleRecursoEntradaDto> horarioDisponible;
}
