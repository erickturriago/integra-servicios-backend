package com.backend.integraservicios.service;

import com.backend.integraservicios.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.integraservicios.dto.modificacion.RecursoModificacionDto;
import com.backend.integraservicios.dto.salida.recurso.RecursoSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IRecursoService {

    Object registrarRecurso(RecursoEntradaDto recurso) throws BadRequestException;
    List<RecursoSalidaDto> listarRecursos() throws BadRequestException;
    RecursoSalidaDto buscarRecursoPorId(Long id) throws ResourceNotFoundException;
    RecursoSalidaDto actualizarRecurso(RecursoModificacionDto recursoModificado) throws ResourceNotFoundException,BadRequestException;
    RecursoSalidaDto eliminarRecurso(Long id) throws ResourceNotFoundException;

}
