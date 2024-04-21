package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.reserva.ReservaEntradaDto;
import com.backend.clinicaodontologica.dto.salida.recurso.RecursoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.reserva.ReservaSalidaDto;
import com.backend.clinicaodontologica.entity.Recurso;
import com.backend.clinicaodontologica.entity.Reserva;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.RecursoRepository;
import com.backend.clinicaodontologica.repository.ReservaRepository;
import com.backend.clinicaodontologica.service.IRecursoService;
import com.backend.clinicaodontologica.service.IReservaService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ReservaService implements IReservaService {

    private final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);
    private final ReservaRepository reservaRepository;
    private ModelMapper modelMapper;

    public ReservaService(ReservaRepository reservaRepository, ModelMapper modelMapper) {
        this.reservaRepository = reservaRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public Long registrarReserva(ReservaEntradaDto reserva) throws BadRequestException {
        LOGGER.info("ReservaEntradaDTO: " + JsonPrinter.toString(reserva));

        return reserva.getId();
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
