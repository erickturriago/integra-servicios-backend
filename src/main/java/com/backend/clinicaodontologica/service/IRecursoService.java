package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IRecursoService {

    String registrarRecurso(RecursoEntradaDto recurso) throws BadRequestException;

    //List<TurnoSalidaDto> listarTurnos();

    //TurnoSalidaDto buscarTurnoPorId(Long id);

    //TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno);

    //void eliminarTurno(Long id) throws ResourceNotFoundException;


}
