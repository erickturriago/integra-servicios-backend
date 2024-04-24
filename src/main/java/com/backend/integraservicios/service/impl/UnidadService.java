package com.backend.integraservicios.service.impl;

import com.backend.integraservicios.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.integraservicios.dto.entrada.unidad.UnidadEntradaDto;
import com.backend.integraservicios.dto.entrada.usuario.UsuarioEntradaDto;
import com.backend.integraservicios.dto.entrada.usuario.UsuarioLoginEntradaDto;
import com.backend.integraservicios.dto.modificacion.UsuarioModificacionEntradaDto;
import com.backend.integraservicios.dto.salida.unidad.UnidadSalidaDto;
import com.backend.integraservicios.dto.salida.usuario.UsuarioSalidaDto;
import com.backend.integraservicios.entity.Dia;
import com.backend.integraservicios.entity.Unidad;
import com.backend.integraservicios.entity.Usuario;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;
import com.backend.integraservicios.repository.DiaRepository;
import com.backend.integraservicios.repository.UnidadRepository;
import com.backend.integraservicios.repository.UsuarioRepository;
import com.backend.integraservicios.service.IUnidadService;
import com.backend.integraservicios.service.IUsuarioService;
import com.backend.integraservicios.utils.JsonPrinter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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
    @PostConstruct
    private void configureMapping() {
        modelMapper.typeMap(UnidadEntradaDto.class, Unidad.class);
        modelMapper.typeMap(Unidad.class, UnidadSalidaDto.class);
    }

}
