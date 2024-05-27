package com.backend.integraservicios.dto.salida;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReservaSalidaDto {
    private Long id;
    private RecursoSalidaDto recurso;
    private UsuarioSalidaDto usuario;
    private String estado;
    private String horaInicio;
    private String horaFin;
    private String fechaReserva;
    private String fechaCreacion;
    private String fechaPrestamo;
    private String fechaDevolucion;
}
