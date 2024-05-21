package com.backend.integraservicios.service;

import com.backend.integraservicios.dto.entrada.reserva.ReservaEntradaDto;
import com.backend.integraservicios.dto.salida.reserva.ReservaSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IReservaService {

    Object registrarReserva(ReservaEntradaDto reserva) throws BadRequestException;

    List<ReservaSalidaDto> listarReservas() throws BadRequestException;

    List<ReservaSalidaDto> listarReservasPorUsuario(Long id);

    //TurnoSalidaDto buscarTurnoPorId(Long id);

    //TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno);

    void eliminarReserva(Long id) throws ResourceNotFoundException;

}
