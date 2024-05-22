package com.backend.integraservicios.service;

import com.backend.integraservicios.dto.entrada.OdontologoEntradaDto;
import com.backend.integraservicios.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.integraservicios.dto.salida.OdontologoSalidaDto;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IOdontologoService {
    List<OdontologoSalidaDto> listarOdontologos();


    OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo);

    OdontologoSalidaDto buscarOdontologoPorId(Long id);

    void eliminarOdontologo(Long id) throws ResourceNotFoundException;

    OdontologoSalidaDto actualizarOdontologo(OdontologoModificacionEntradaDto odontologoModificacionEntradaDto) throws ResourceNotFoundException;
}
