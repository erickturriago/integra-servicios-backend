package com.backend.integraservicios.service.impl;

import com.backend.integraservicios.dto.entrada.reserva.ReservaEntradaDto;
import com.backend.integraservicios.dto.salida.recurso.RecursoSalidaDto;
import com.backend.integraservicios.dto.salida.reserva.ReservaSalidaDto;
import com.backend.integraservicios.dto.salida.usuario.UsuarioSalidaDto;
import com.backend.integraservicios.entity.Recurso;
import com.backend.integraservicios.entity.Reserva;
import com.backend.integraservicios.entity.Unidad;
import com.backend.integraservicios.entity.Usuario;
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
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import java.time.Duration;
import java.time.LocalDateTime;
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

        Unidad unidadReserva = unidadRepository.findById(recursoReserva.getUnidad().getId()).orElse(null);

        LocalDateTime fechaHoraInicial = LocalDateTime.parse(reserva.getFechaInicio() + "T" + reserva.getHoraInicio());
        LocalDateTime fechaHoraFinal = LocalDateTime.parse(reserva.getFechaFin() + "T" + reserva.getHoraFin());
        long tiempoReserva = Duration.between(fechaHoraInicial, fechaHoraFinal).toMinutes();
        LOGGER.info("Tiempo reserva: "+tiempoReserva);

        if(tiempoReserva>unidadReserva.getTiempoMaximo() || tiempoReserva<unidadReserva.getTiempoMinimo()){
            return "Tiempo de reserva inválido";
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
        return reservas;
    }

    @Override
    public List<ReservaSalidaDto> listarReservasPorRecurso(Long id) {
        List<ReservaSalidaDto> reservas = reservaRepository.findByRecurso_Id(id).stream()
                .map(r -> modelMapper.map(r, ReservaSalidaDto.class)).toList();

        LOGGER.info("Reserva por recurso");
        return reservas;
    }


    @Override
    public void eliminarReserva(Long id) throws ResourceNotFoundException {

    }

    @PostConstruct
    private void configureMapping() {
        modelMapper.typeMap(ReservaService.class, Reserva.class);
        modelMapper.typeMap(Reserva.class, ReservaSalidaDto.class);
    }
}
