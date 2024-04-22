package com.backend.integraservicios.service.impl;

import com.backend.integraservicios.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.integraservicios.dto.salida.recurso.RecursoSalidaDto;
import com.backend.integraservicios.entity.Recurso;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.repository.RecursoRepository;
import com.backend.integraservicios.service.IRecursoService;
import com.backend.integraservicios.utils.JsonPrinter;
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
        LOGGER.info("Recurso guardado: {}", recursoGuardado);
        return recursoGuardado.getId();
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
