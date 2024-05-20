package com.backend.integraservicios.service;

import com.backend.integraservicios.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.integraservicios.dto.entrada.unidad.UnidadEntradaDto;
import com.backend.integraservicios.dto.modificacion.UnidadModificacionDto;
import com.backend.integraservicios.dto.salida.unidad.UnidadSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IUnidadService {

    Object registrarUnidad(UnidadEntradaDto unidad) throws BadRequestException;

    List<UnidadSalidaDto> listarUnidades();

    UnidadSalidaDto buscarUnidadPorId(Long id) throws ResourceNotFoundException;

    UnidadSalidaDto actualizarUnidad(UnidadModificacionDto unidad) throws ResourceNotFoundException,BadRequestException;

    UnidadSalidaDto eliminarUnidad(Long id) throws ResourceNotFoundException;


}
