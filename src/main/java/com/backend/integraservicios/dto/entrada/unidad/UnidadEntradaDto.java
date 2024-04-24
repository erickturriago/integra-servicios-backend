package com.backend.integraservicios.dto.entrada.unidad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UnidadEntradaDto {
    //private Long id;
    private String nombre;
    private String tipo;
    private int tiempoMinimo;
    private int tiempoMaximo;
    private String horaInicio;
    private String horaFinal;
    private List<Long> diasDisponibles;
}
