package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.recurso.RecursoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;

import java.util.List;

public interface IRecursoService {

    Long registrarRecurso(RecursoEntradaDto recurso) throws BadRequestException;

    List<RecursoSalidaDto> listarRecursos() throws BadRequestException;

    //TurnoSalidaDto buscarTurnoPorId(Long id);

    //TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno);

    //void eliminarTurno(Long id) throws ResourceNotFoundException;


}
