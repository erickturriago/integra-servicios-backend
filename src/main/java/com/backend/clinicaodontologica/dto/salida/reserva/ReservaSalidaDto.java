package com.backend.clinicaodontologica.dto.salida.reserva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReservaSalidaDto {
    private Long id;
    private Long idRecurso;
    private Long idUsuario;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String horaInicio;
    private String horaFin;
    private LocalDate fechaCreacion;
    private LocalDate fechaDevolucion;
}
