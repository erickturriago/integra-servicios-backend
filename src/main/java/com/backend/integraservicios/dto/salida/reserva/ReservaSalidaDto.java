package com.backend.integraservicios.dto.salida.reserva;

import com.backend.integraservicios.dto.salida.recurso.RecursoSalidaDto;
import com.backend.integraservicios.dto.salida.usuario.UsuarioSalidaDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReservaSalidaDto {
    private Long id;
    private RecursoSalidaDto recurso;
    private UsuarioSalidaDto usuario;
    private String estado;
    private String fechaInicio;
    private String fechaFin;
    private String horaInicio;
    private String horaFin;
    private String fechaCreacion;
    private String fechaDevolucion;
}
