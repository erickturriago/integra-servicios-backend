package com.backend.integraservicios.service.impl;

import com.backend.integraservicios.dto.entrada.horarioDisponbielRecurso.HorarioDisponibleRecursoEntradaDto;
import com.backend.integraservicios.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.integraservicios.dto.salida.recurso.RecursoSalidaDto;
import com.backend.integraservicios.entity.Dia;
import com.backend.integraservicios.entity.HorarioDisponibleRecurso;
import com.backend.integraservicios.entity.Recurso;
import com.backend.integraservicios.entity.Unidad;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.repository.DiaRepository;
import com.backend.integraservicios.repository.HorarioDisponibleRecursoRepository;
import com.backend.integraservicios.repository.RecursoRepository;
import com.backend.integraservicios.repository.UnidadRepository;
import com.backend.integraservicios.service.IRecursoService;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Service
public class RecursoService implements IRecursoService {

    private final Logger LOGGER = LoggerFactory.getLogger(RecursoService.class);
    private final RecursoRepository recursoRepository;
    private final DiaRepository diaRepository;
    private final UnidadRepository unidadRepository;
    private final HorarioDisponibleRecursoRepository horarioDisponibleRecursoRepository;
    private ModelMapper modelMapper;


    @Override
    public Object registrarRecurso(RecursoEntradaDto recurso) throws BadRequestException {

        Unidad unidadRecurso = unidadRepository.findById(recurso.getUnidad()).orElse(null);

        if(unidadRecurso == null){
            return "La unidad no existe";
        }

        List<HorarioDisponibleRecurso> horarios = new ArrayList<>();
        for (HorarioDisponibleRecursoEntradaDto horarioDto : recurso.getHorarioDisponible()) {
            Dia dia = diaRepository.findById(horarioDto.getDia()).orElseThrow(); // Obtener entidad Dia
            HorarioDisponibleRecurso horario = new HorarioDisponibleRecurso();

            Boolean flagDia = false;
            for(Dia diaUnidad: unidadRecurso.getDiasDisponibles()){
                if(diaUnidad.getId()==dia.getId()){
                    flagDia=true;
                }
            }
            if(!flagDia){
                return "Día de disponibilidad inválido por dias de unidad";
            }

            if(LocalTime.parse(horarioDto.getHoraInicio()).isBefore(LocalTime.parse(unidadRecurso.getHoraInicio()))){
                return "Hora de inicio inválida por horario de unidad";
            }
            if(LocalTime.parse(horarioDto.getHoraFin()).isAfter(LocalTime.parse(unidadRecurso.getHoraFinal()))){
                return "Hora de fin inválida por horario de unidad";
            }
            horario.setDia(dia);
            horario.setHoraInicio(horarioDto.getHoraInicio());
            horario.setHoraFin(horarioDto.getHoraFin());
            // Guardar el horario en la base de datos
            horario = horarioDisponibleRecursoRepository.save(horario);
            horarios.add(horario);
        }

        Recurso recursoEntidad = modelMapper.map(recurso, Recurso.class);
        recursoEntidad.setUnidad(unidadRecurso);
        recursoEntidad.setHorarioDisponible(horarios);
        Recurso recursoGuardado = recursoRepository.save(recursoEntidad);
        LOGGER.info("Recurso guardado: {}", JsonPrinter.toString(recursoEntidad));
        return modelMapper.map(recursoGuardado,RecursoSalidaDto.class);
    }

    @Override
    public List<RecursoSalidaDto> listarRecursos() throws BadRequestException{
        List<RecursoSalidaDto> recursos = recursoRepository.findAll().stream()
                .map(r -> modelMapper.map(r, RecursoSalidaDto.class)).toList();

        //LOGGER.info("Listado de todos los recursos: {}", JsonPrinter.toString(recursos));

        return recursos;
    }

    @PostConstruct
    private void configureMapping() {
        modelMapper.typeMap(RecursoEntradaDto.class, Recurso.class);
        modelMapper.typeMap(Recurso.class, RecursoSalidaDto.class);
    }
}
