package com.backend.integraservicios.service;

import com.backend.integraservicios.dto.entrada.ReservaEntradaDto;
import com.backend.integraservicios.dto.modificacion.ReservaModificacionDto;
import com.backend.integraservicios.dto.salida.ReservaSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IReservaService {

    Object registrarReserva(ReservaEntradaDto reserva) throws BadRequestException;
    ReservaSalidaDto actualizarReserva(ReservaModificacionDto reservaModificacionDto) throws ResourceNotFoundException;
    List<ReservaSalidaDto> listarReservas() throws BadRequestException;
    List<ReservaSalidaDto> listarReservasPorUsuario(Long id);
    List<ReservaSalidaDto> listarReservasPorRecurso(Long id);
    ReservaSalidaDto buscarReservaPorId(Long id) throws ResourceNotFoundException;
    ReservaSalidaDto cancelarReserva(Long id) throws ResourceNotFoundException;

}
