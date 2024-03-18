package com.backend.clinicaodontologica.dto.entrada.recurso;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecursoEntradaDto {
    private List<String> horarios;

    public RecursoEntradaDto() {}
    public RecursoEntradaDto(List<String> horarios) {
        this.horarios = horarios;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }
}
