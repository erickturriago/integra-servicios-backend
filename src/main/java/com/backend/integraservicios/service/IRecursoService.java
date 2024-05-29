package com.backend.integraservicios.service;

import com.backend.integraservicios.dto.entrada.RecursoEntradaDto;
import com.backend.integraservicios.dto.entrada.RecursoExternoEntradaDto;
import com.backend.integraservicios.dto.modificacion.RecursoModificacionDto;
import com.backend.integraservicios.dto.salida.RecursoExternoSalidaDto;
import com.backend.integraservicios.dto.salida.RecursoSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IRecursoService {

    Object registrarRecurso(RecursoEntradaDto recurso) throws BadRequestException;
    Object registrarRecursosExternos(List<RecursoExternoEntradaDto> recursos) throws BadRequestException;
    List<RecursoSalidaDto> listarRecursos() throws BadRequestException;
    List<RecursoExternoSalidaDto> listarRecursosExternos() throws BadRequestException;
    RecursoSalidaDto buscarRecursoPorId(Long id) throws ResourceNotFoundException;
    RecursoSalidaDto actualizarRecurso(RecursoModificacionDto recursoModificado) throws ResourceNotFoundException,BadRequestException;
    RecursoSalidaDto eliminarRecurso(Long id) throws ResourceNotFoundException;

}
