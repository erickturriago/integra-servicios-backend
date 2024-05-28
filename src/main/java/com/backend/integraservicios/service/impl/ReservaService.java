package com.backend.integraservicios.service.impl;

import com.backend.integraservicios.dto.entrada.ReservaEntradaDto;
import com.backend.integraservicios.dto.modificacion.ReservaModificacionDto;
import com.backend.integraservicios.dto.salida.RecursoSalidaDto;
import com.backend.integraservicios.dto.salida.ReservaSalidaDto;
import com.backend.integraservicios.dto.salida.UnidadSalidaDto;
import com.backend.integraservicios.dto.salida.UsuarioSalidaDto;
import com.backend.integraservicios.entity.*;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;
import com.backend.integraservicios.repository.RecursoRepository;
import com.backend.integraservicios.repository.ReservaRepository;
import com.backend.integraservicios.repository.UnidadRepository;
import com.backend.integraservicios.repository.UsuarioRepository;
import com.backend.integraservicios.service.IReservaService;
import com.backend.integraservicios.utils.JsonPrinter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Service
public class ReservaService implements IReservaService {

    private final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);
    private final ReservaRepository reservaRepository;
    private final RecursoRepository recursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadRepository unidadRepository;
    private final UnidadService unidadService;
    private ModelMapper modelMapper;

    @Override
    public Object registrarReserva(ReservaEntradaDto reserva) throws BadRequestException {
        LOGGER.info("ReservaEntradaDTO: " + JsonPrinter.toString(reserva));

        Recurso recursoReserva = recursoRepository.findById(reserva.getIdRecurso()).orElse(null);
        Usuario usuarioReserva = usuarioRepository.findById(reserva.getIdUsuario()).orElse(null);

        if(recursoReserva==null){
            return "El recurso no existe";
        }
        if(usuarioReserva==null){
            return "El usuario no existe";
        }


        Reserva reservaEntidad = modelMapper.map(reserva,Reserva.class);
        Reserva reservaGuardada = reservaRepository.save(reservaEntidad);

        ReservaSalidaDto reservaSalida = modelMapper.map(reservaGuardada,ReservaSalidaDto.class);
        reservaSalida.setUsuario(modelMapper.map(usuarioReserva, UsuarioSalidaDto.class));
        reservaSalida.setRecurso(modelMapper.map(recursoReserva,RecursoSalidaDto.class));


        return reservaSalida;
    }

    @Override
    public ReservaSalidaDto actualizarReserva(ReservaModificacionDto reservaModificacionDto) throws ResourceNotFoundException{
        LOGGER.info("ReservaModificacionDto: " + JsonPrinter.toString(reservaModificacionDto));

        Recurso recursoReserva = recursoRepository.findById(reservaModificacionDto.getIdRecurso()).orElse(null);
        Usuario usuarioReserva = usuarioRepository.findById(reservaModificacionDto.getIdUsuario()).orElse(null);

        if(recursoReserva==null){
            throw new ResourceNotFoundException("El recurso no existe");
        }
        if(usuarioReserva==null){
            throw new ResourceNotFoundException("El usuario no existe");
        }

        Reserva reservaAActualizar = modelMapper.map(reservaModificacionDto,Reserva.class);
        reservaAActualizar.setUsuario(usuarioReserva);
        reservaAActualizar.setRecurso(recursoReserva);

        return modelMapper.map(reservaRepository.save(reservaAActualizar),ReservaSalidaDto.class);
    }


    @Override
    public List<ReservaSalidaDto> listarReservas() throws BadRequestException {
        List<ReservaSalidaDto> reservas = reservaRepository.findAll().stream()
                .map(r -> modelMapper.map(r, ReservaSalidaDto.class)).toList();

        for (ReservaSalidaDto reserva: reservas) {
            reserva.setRecurso(modelMapper.map(recursoRepository.findById(reserva.getRecurso().getId()).orElse(null),RecursoSalidaDto.class));
            reserva.setUsuario(modelMapper.map(usuarioRepository.findById(reserva.getUsuario().getId()).orElse(null),UsuarioSalidaDto.class));
        }

        LOGGER.info("Listado de todas las reservas: {}", JsonPrinter.toString(reservas));

        return reservas;
    }

    @Override
    public List<ReservaSalidaDto> listarReservasPorUsuario(Long id) {
        List<ReservaSalidaDto> reservas = reservaRepository.findByUsuario_Id(id).stream()
                .map(r -> modelMapper.map(r, ReservaSalidaDto.class)).toList();

        LOGGER.info("Reservas por Usuario");
        LOGGER.info(JsonPrinter.toString(reservas));
        return reservas;
    }

    @Override
    public List<ReservaSalidaDto> listarReservasPorRecurso(Long id) {
        List<ReservaSalidaDto> reservas = reservaRepository.findByRecurso_Id(id).stream()
                .map(r -> modelMapper.map(r, ReservaSalidaDto.class)).toList();

        LOGGER.info("Reserva por recurso");
        LOGGER.info(JsonPrinter.toString(reservas));
        return reservas;
    }

    @Override
    public ReservaSalidaDto buscarReservaPorId(Long id) throws ResourceNotFoundException{
        Reserva reservaBuscada = reservaRepository.findById(id).orElse(null);

        ReservaSalidaDto reservaSalidaDto = null;
        if (reservaBuscada != null) {
            reservaSalidaDto = modelMapper.map(reservaBuscada, ReservaSalidaDto.class);
            LOGGER.info("Reserva encontrado: {}", reservaSalidaDto);
        } else {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
            throw new ResourceNotFoundException("El id no se encuentra registrado en la base de datos");
        }

        return reservaSalidaDto;
    }


    @Override
    public ReservaSalidaDto cancelarReserva(Long id) throws ResourceNotFoundException {
        Reserva reservaACancelar = reservaRepository.findById(id).orElse(null);
        if (reservaACancelar != null) {
            reservaACancelar.setEstado("Cancelada");
            reservaRepository.save(reservaACancelar);
            LOGGER.warn("Se ha cancelado la reserva con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado la reserva con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado la reserva con id " + id);
        }
        return modelMapper.map(reservaACancelar,ReservaSalidaDto.class);
    }

    @PostConstruct
    private void configureMapping() {
        modelMapper.typeMap(ReservaService.class, Reserva.class);
        modelMapper.typeMap(Reserva.class, ReservaSalidaDto.class);
    }
}
