package com.backend.integraservicios.dto.entrada.reserva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReservaEntradaDto {

    private Long id;

    @NotNull(message = "El ID del recurso no puede ser nulo")
    private Long idRecurso;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long idUsuario;

    @NotBlank(message = "El estado de la reserva no puede estar vacío")
    private String estado;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin no puede ser nula")
    @FutureOrPresent(message = "La fecha de fin debe ser hoy o en el futuro")
    private LocalDate fechaFin;

    @NotBlank(message = "La hora de inicio no puede estar vacía")
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$", message = "La hora de inicio debe tener el formato HH:mm")
    private String horaInicio;

    @NotBlank(message = "La hora de fin no puede estar vacía")
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$", message = "La hora de fin debe tener el formato HH:mm")
    private String horaFin;

    @NotNull(message = "La fecha de creación no puede ser nula")
    @PastOrPresent(message = "La fecha de creación debe ser hoy o en el pasado")
    private LocalDateTime fechaCreacion;

    private LocalDate fechaDevolucion;
}
