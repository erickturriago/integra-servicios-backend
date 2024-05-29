package com.backend.integraservicios.dto.entrada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RecursoExternoEntradaDto {
    Long id;
    String consecutivoRecurso;
    String codUnidad;
    String consecutivoTDR;
    String codHorario;
    String nombreRecurso;
    String estadoRecurso;
    String valorHora;
}
