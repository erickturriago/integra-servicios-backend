package com.backend.integraservicios.dto.salida;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RecursoExternoSalidaDto {
    Long id;
    String consecutivoRecurso;
    String codUnidad;
    String consecutivoTDR;
    String codHorario;
    String nombreRecurso;
    String estadoRecurso;
    String valorHora;
}
