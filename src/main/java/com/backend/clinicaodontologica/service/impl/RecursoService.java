package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Recurso;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.RecursoRepository;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.IRecursoService;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RecursoService implements IRecursoService {

    private final Logger LOGGER = LoggerFactory.getLogger(RecursoService.class);
    private final RecursoRepository recursoRepository;
    private ModelMapper modelMapper;

    public RecursoService(RecursoRepository recursoRepository, ModelMapper modelMapper) {
        this.recursoRepository = recursoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public String registrarRecurso(RecursoEntradaDto recurso) throws BadRequestException {
        Recurso recursoEntidad = modelMapper.map(recurso,Recurso.class);
        Recurso recursoGuardado = recursoRepository.save(recursoEntidad);
        LOGGER.info("Recurso guardado: {}", recursoGuardado);
        return "Registrado";
    }

    private void configureMapping() {
        modelMapper.typeMap(RecursoEntradaDto.class, Recurso.class);
    }
}
