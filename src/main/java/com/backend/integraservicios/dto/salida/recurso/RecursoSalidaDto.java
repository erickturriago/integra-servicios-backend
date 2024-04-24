package com.backend.integraservicios.dto.salida.recurso;

import com.backend.integraservicios.dto.salida.horarioDisponbielRecurso.HorarioDisponibleRecursoSalidaDto;
import com.backend.integraservicios.entity.HorarioDisponibleRecurso;
import com.backend.integraservicios.entity.Unidad;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecursoSalidaDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imageUrl;
    private Unidad unidad;
    private List<HorarioDisponibleRecursoSalidaDto> horarioDisponible;
}
