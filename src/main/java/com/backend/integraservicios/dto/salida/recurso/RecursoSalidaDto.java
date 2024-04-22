package com.backend.integraservicios.dto.salida.recurso;

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
    private String horaInicio;
    private String horaFin;
    private int tiempoMinimoReserva;
    private int tiempoMaximoReserva;
    private List<String> diasDisponible;
}
