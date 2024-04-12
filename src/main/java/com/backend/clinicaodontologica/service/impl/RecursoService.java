package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.recurso.RecursoSalidaDto;
import com.backend.clinicaodontologica.entity.Recurso;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.repository.RecursoRepository;
import com.backend.clinicaodontologica.service.IRecursoService;
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
    public Long registrarRecurso(RecursoEntradaDto recurso) throws BadRequestException {
        Recurso recursoEntidad = modelMapper.map(recurso,Recurso.class);
        Recurso recursoGuardado = recursoRepository.save(recursoEntidad);
<<<<<<< HEAD
        LOGGER.info("Recurso guardado: {}", recursoGuardado);
        return recursoGuardado.getId();
=======
        LOGGER.info("Recurso guardado: {}", JsonPrinter.toString(recursoGuardado));
        return "Registrado";
>>>>>>> fd5e0f20ebc98e33f0375b20aab2623dcbf62b5c
    }

    @Override
    public List<RecursoSalidaDto> listarRecursos() throws BadRequestException{
        List<RecursoSalidaDto> recursos = recursoRepository.findAll().stream()
                .map(r -> modelMapper.map(r, RecursoSalidaDto.class)).toList();

        LOGGER.info("Listado de todos los recursos: {}", JsonPrinter.toString(recursos));

        return recursos;
    }

    private void configureMapping() {
        modelMapper.typeMap(RecursoEntradaDto.class, Recurso.class);
    }
}
