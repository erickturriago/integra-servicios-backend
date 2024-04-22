package com.backend.integraservicios.service.impl;

import com.backend.integraservicios.dto.entrada.reserva.ReservaEntradaDto;
import com.backend.integraservicios.dto.salida.recurso.RecursoSalidaDto;
import com.backend.integraservicios.dto.salida.reserva.ReservaSalidaDto;
import com.backend.integraservicios.dto.salida.usuario.UsuarioSalidaDto;
import com.backend.integraservicios.entity.Recurso;
import com.backend.integraservicios.entity.Reserva;
import com.backend.integraservicios.entity.Usuario;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;
import com.backend.integraservicios.repository.RecursoRepository;
import com.backend.integraservicios.repository.ReservaRepository;
import com.backend.integraservicios.repository.UsuarioRepository;
import com.backend.integraservicios.service.IReservaService;
import com.backend.integraservicios.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ReservaService implements IReservaService {

    private final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);
    private final ReservaRepository reservaRepository;
    private final RecursoRepository recursoRepository;
    private final UsuarioRepository  usuarioRepository;
    private ModelMapper modelMapper;

    public ReservaService(ReservaRepository reservaRepository,RecursoRepository recursoRepository, UsuarioRepository  usuarioRepository, ModelMapper modelMapper) {
        this.reservaRepository = reservaRepository;
        this.recursoRepository=recursoRepository;
        this.usuarioRepository=usuarioRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public Object registrarReserva(ReservaEntradaDto reserva) throws BadRequestException {
        LOGGER.info("ReservaEntradaDTO: " + JsonPrinter.toString(reserva));

        Recurso recursoReserva = recursoRepository.findById(reserva.getIdRecurso()).orElse(null);
        Usuario usuarioReserva = usuarioRepository.findById(reserva.getIdUsuario()).orElse(null);;

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
    public List<ReservaSalidaDto> listarReservas() throws BadRequestException {
        return null;
    }

    @Override
    public void eliminarReserva(Long id) throws ResourceNotFoundException {

    }


    private void configureMapping() {
        modelMapper.typeMap(ReservaService.class, Reserva.class);
        modelMapper.typeMap(Reserva.class, ReservaSalidaDto.class);
    }
}
