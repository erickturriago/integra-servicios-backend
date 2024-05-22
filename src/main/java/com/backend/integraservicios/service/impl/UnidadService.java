package com.backend.integraservicios.service.impl;

import com.backend.integraservicios.dto.entrada.UnidadEntradaDto;
import com.backend.integraservicios.dto.modificacion.UnidadModificacionDto;
import com.backend.integraservicios.dto.salida.UnidadSalidaDto;
import com.backend.integraservicios.entity.Dia;
import com.backend.integraservicios.entity.Unidad;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;
import com.backend.integraservicios.repository.DiaRepository;
import com.backend.integraservicios.repository.UnidadRepository;
import com.backend.integraservicios.service.IUnidadService;
import com.backend.integraservicios.utils.JsonPrinter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Service
public class UnidadService implements IUnidadService {

    private final Logger LOGGER = LoggerFactory.getLogger(UnidadService.class);
    private UnidadRepository unidadRepository;
    private DiaRepository diaRepository;
    private ModelMapper modelMapper;

    @Override
    public UnidadSalidaDto registrarUnidad(UnidadEntradaDto unidad) throws BadRequestException {
        // Convertir DTO a entidad
        Unidad unidadEntidad = modelMapper.map(unidad,Unidad.class);

        // Obtener los días de la base de datos
        List<Dia> diasDisponibles = (List<Dia>) diaRepository.findAllById(unidad.getDiasDisponibles());

        unidadEntidad.setDiasDisponibles(diasDisponibles);

        // Guardar la unidad
        return modelMapper.map(unidadRepository.save(unidadEntidad),UnidadSalidaDto.class);
    }

    @Override
    public List<UnidadSalidaDto> listarUnidades() {
        List<UnidadSalidaDto> unidades = unidadRepository.findAll().stream()
                .map(u -> modelMapper.map(u, UnidadSalidaDto.class))
                .collect(Collectors.toList());

        return unidades;
    }

    @Override
    public UnidadSalidaDto buscarUnidadPorId(Long id) throws ResourceNotFoundException{
        Unidad unidadBuscada = unidadRepository.findById(id).orElse(null);

        UnidadSalidaDto unidadSalidaDto = null;
        if (unidadBuscada != null) {
            unidadSalidaDto = modelMapper.map(unidadBuscada, UnidadSalidaDto.class);
            LOGGER.info("Recurso encontrado: {}", unidadSalidaDto);
        } else {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
            throw new ResourceNotFoundException("El id no se encuentra registrado en la base de datos");
        }

        return unidadSalidaDto;
    }

    @Override
    public UnidadSalidaDto actualizarUnidad(UnidadModificacionDto unidad) throws ResourceNotFoundException,BadRequestException {
        Unidad unidadComprobacion = unidadRepository.findById(unidad.getId()).orElse(null);

        UnidadSalidaDto unidadSalidaDto = null;

        LOGGER.info("Unidad modificacion entrada: {}", JsonPrinter.toString(unidad));

        List<Dia> listaDias = new ArrayList<Dia>();

        if(unidadComprobacion!=null){

            for(Long idDia:unidad.getDiasDisponibles()){
                listaDias.add(diaRepository.findById(idDia).orElse(null));
            }
            Unidad unidadGuardar = modelMapper.map(unidad,Unidad.class);

            unidadGuardar.setDiasDisponibles(listaDias);

            unidadSalidaDto = modelMapper.map(unidadRepository.save(unidadGuardar),UnidadSalidaDto.class);
        }
        else{
            throw new ResourceNotFoundException("La unidad no existe");
        }

        LOGGER.info("Unidad actualizada: {}", JsonPrinter.toString(unidadSalidaDto));
        return unidadSalidaDto;
    }

    @Override
    public UnidadSalidaDto eliminarUnidad(Long id) throws ResourceNotFoundException {
        UnidadSalidaDto unidadAEliminar = null;
        unidadAEliminar = buscarUnidadPorId(id);
        if (unidadAEliminar != null) {
            unidadRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado la unidad con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el recurso con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el recurso con id " + id);
        }
        return unidadAEliminar;
    }

    @PostConstruct
    private void configureMapping() {
        modelMapper.typeMap(UnidadEntradaDto.class, Unidad.class);
        modelMapper.typeMap(Unidad.class, UnidadSalidaDto.class);
        modelMapper.typeMap(UnidadModificacionDto.class,Unidad.class);
    }

}
