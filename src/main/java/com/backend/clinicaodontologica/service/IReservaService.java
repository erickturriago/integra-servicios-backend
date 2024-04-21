package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.reserva.ReservaEntradaDto;
import com.backend.clinicaodontologica.dto.salida.recurso.RecursoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.reserva.ReservaSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IReservaService {

    Long registrarReserva(ReservaEntradaDto reserva) throws BadRequestException;

    List<ReservaSalidaDto> listarReservas() throws BadRequestException;

    //TurnoSalidaDto buscarTurnoPorId(Long id);

    //TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno);

    void eliminarReserva(Long id) throws ResourceNotFoundException;

}
