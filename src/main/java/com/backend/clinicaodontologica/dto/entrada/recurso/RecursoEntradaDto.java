package com.backend.clinicaodontologica.dto.entrada.recurso;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RecursoEntradaDto {
    private Long id;
    private String nombre;
    private String horaInicio;
    private String horaFin;
    private int tiempoMinimoReserva;
    private int tiempoMaximoReserva;
    private List<String> diasDisponible;
}
