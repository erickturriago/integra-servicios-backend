package com.backend.integraservicios.dto.salida;

import com.backend.integraservicios.entity.Dia;
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
public class UnidadSalidaDto {
    private Long id;
    private String nombre;
    private String tipo;
    private int tiempoMinimo;
    private int tiempoMaximo;
    private String horaInicio;
    private String horaFinal;
    private List<Dia> diasDisponibles;
}
