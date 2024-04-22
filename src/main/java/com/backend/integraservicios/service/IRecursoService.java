package com.backend.integraservicios.service;

import com.backend.integraservicios.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.integraservicios.dto.salida.recurso.RecursoSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;

import java.util.List;

public interface IRecursoService {

    Long registrarRecurso(RecursoEntradaDto recurso) throws BadRequestException;

    List<RecursoSalidaDto> listarRecursos() throws BadRequestException;

    //TurnoSalidaDto buscarTurnoPorId(Long id);

    //TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno);

    //void eliminarTurno(Long id) throws ResourceNotFoundException;


}
