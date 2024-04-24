package com.backend.integraservicios.service;

import com.backend.integraservicios.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.integraservicios.dto.entrada.unidad.UnidadEntradaDto;
import com.backend.integraservicios.dto.salida.unidad.UnidadSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;

public interface IUnidadService {

    Object registrarUnidad(UnidadEntradaDto unidad) throws BadRequestException;

    //List<RecursoSalidaDto> listarRecursos() throws BadRequestException;

    //TurnoSalidaDto buscarTurnoPorId(Long id);

    //TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno);

    //void eliminarTurno(Long id) throws ResourceNotFoundException;


}
