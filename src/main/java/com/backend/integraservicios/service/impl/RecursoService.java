package com.backend.integraservicios.service.impl;

import com.backend.integraservicios.dto.entrada.HorarioDisponibleRecursoEntradaDto;
import com.backend.integraservicios.dto.entrada.RecursoEntradaDto;
import com.backend.integraservicios.dto.entrada.RecursoExternoEntradaDto;
import com.backend.integraservicios.dto.modificacion.HorarioDisponibleRecursoModificacionDto;
import com.backend.integraservicios.dto.modificacion.RecursoModificacionDto;
import com.backend.integraservicios.dto.salida.RecursoExternoSalidaDto;
import com.backend.integraservicios.dto.salida.RecursoSalidaDto;
import com.backend.integraservicios.entity.*;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;
import com.backend.integraservicios.repository.*;
import com.backend.integraservicios.service.IRecursoService;
import com.backend.integraservicios.utils.JsonPrinter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Service
public class RecursoService implements IRecursoService {

    private final Logger LOGGER = LoggerFactory.getLogger(RecursoService.class);
    private final RecursoRepository recursoRepository;
    private final RecursoExternoRepository recursoExternoRepository;
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
    public Object registrarRecursosExternos(List<RecursoExternoEntradaDto> recursos) throws BadRequestException {
        List<RecursoExternoSalidaDto> recursosExternosSalida = new ArrayList<>();
        for(RecursoExternoEntradaDto recurso : recursos){
            RecursoExterno recursoEntidad = modelMapper.map(recurso,RecursoExterno.class);
            recursosExternosSalida.add(modelMapper.map(recursoExternoRepository.save(recursoEntidad),RecursoExternoSalidaDto.class));
        }
        return recursosExternosSalida;
    }


    @Override
    public List<RecursoSalidaDto> listarRecursos() throws BadRequestException{
        List<RecursoSalidaDto> recursos = recursoRepository.findAll().stream()
                .map(r -> modelMapper.map(r, RecursoSalidaDto.class)).toList();

        return recursos;
    }

    @Override
    public List<RecursoExternoSalidaDto> listarRecursosExternos() throws BadRequestException {
        List<RecursoExternoSalidaDto> recursos = recursoExternoRepository.findAll().stream()
                .map(r -> modelMapper.map(r, RecursoExternoSalidaDto.class)).toList();

        return recursos;
    }


    @Override
    public RecursoSalidaDto eliminarRecurso(Long id) throws ResourceNotFoundException {
        RecursoSalidaDto recursoAEliminar = null;
        recursoAEliminar = buscarRecursoPorId(id);
        if (recursoAEliminar != null) {
            recursoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el recurso con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el recurso con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el recurso con id " + id);
        }
        return recursoAEliminar;
    }

    @Override
    public RecursoSalidaDto buscarRecursoPorId(Long id) throws ResourceNotFoundException{
        Recurso recursoBuscado = recursoRepository.findById(id).orElse(null);

        RecursoSalidaDto recursoSalidaDto = null;
        if (recursoBuscado != null) {
            recursoSalidaDto = modelMapper.map(recursoBuscado, RecursoSalidaDto.class);
            LOGGER.info("Recurso encontrado: {}", recursoSalidaDto);
        } else {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
            throw new ResourceNotFoundException("El id no se encuentra registrado en la base de datos");
        }

        return recursoSalidaDto;
    }

    @Override
    public RecursoSalidaDto actualizarRecurso(RecursoModificacionDto recursoModificado) throws ResourceNotFoundException,BadRequestException{

        //Comprobar si el recurso existe
        Recurso recursoComprobacion = recursoRepository.findById(recursoModificado.getId()).orElse(null);
        if(recursoComprobacion==null){
            LOGGER.error("El id del recurso no se encuentra registrado en la base de datos");
            throw new ResourceNotFoundException("El id del recurso no se encuentra registrado en la base de datos");
        }

        //Validar si la unidad existe
        Unidad unidadModificacion = unidadRepository.findById(recursoModificado.getUnidad()).orElse(null);
        if(unidadModificacion==null){
            LOGGER.error("El id de la unidad no se encuentra registrada en la base de datos");
            throw new ResourceNotFoundException("El id de la unidad no se encuentra registrada en la base de datos");
        }

        List<HorarioDisponibleRecurso> horarios = new ArrayList<>();
        for (HorarioDisponibleRecursoModificacionDto horarioModificacionDto : recursoModificado.getHorarioDisponible()) {
            Dia dia = diaRepository.findById(horarioModificacionDto.getDia()).orElseThrow(); // Obtener entidad Dia
            HorarioDisponibleRecurso horario = new HorarioDisponibleRecurso();

            Boolean flagDia = false;
            for(Dia diaUnidad: unidadModificacion.getDiasDisponibles()){
                if(diaUnidad.getId()==dia.getId()){
                    flagDia=true;
                }
            }
            if(!flagDia){
                //return "Día de disponibilidad inválido por dias de unidad";
                throw new BadRequestException("Día de disponibilidad inválido por dias de unidad");
            }
            if(LocalTime.parse(horarioModificacionDto.getHoraInicio()).isBefore(LocalTime.parse(unidadModificacion.getHoraInicio()))){
                //return "Hora de inicio inválida por horario de unidad";
                throw new BadRequestException("Hora de inicio inválida por horario de unidad");
            }
            if(LocalTime.parse(horarioModificacionDto.getHoraFin()).isAfter(LocalTime.parse(unidadModificacion.getHoraFinal()))){
                //return "Hora de fin inválida por horario de unidad";
                throw new BadRequestException("Hora de fin inválida por horario de unidad");
            }
            horario.setDia(dia);
            horario.setHoraInicio(horarioModificacionDto.getHoraInicio());
            horario.setHoraFin(horarioModificacionDto.getHoraFin());
            // Guardar el horario en la base de datos
            //horario = horarioDisponibleRecursoRepository.save(horario);
            horarios.add(horario);
        }

        recursoComprobacion.getHorarioDisponible().clear();

        for(HorarioDisponibleRecurso horarioRecurso : recursoComprobacion.getHorarioDisponible()){
            horarioDisponibleRecursoRepository.delete(horarioRecurso);
        }

        for(HorarioDisponibleRecurso horario: horarios){
            horarioDisponibleRecursoRepository.save(horario);
        }

        Recurso recursoActualizar = modelMapper.map(recursoModificado,Recurso.class);
        recursoActualizar.setUnidad(unidadModificacion);
        recursoActualizar.setHorarioDisponible(horarios);

        RecursoSalidaDto recursoActualizadoSalida = modelMapper.map(recursoRepository.save(recursoActualizar),RecursoSalidaDto.class);

        //Eliminar entidades de horario que no están siendo usados por ningun Recurso
        List<HorarioDisponibleRecurso> horariosUtilizados = recursoRepository.findAll().stream()
                .flatMap(recurso -> recurso.getHorarioDisponible().stream())
                .distinct()
                .collect(Collectors.toList());

        // Obtener todos los horarios disponibles
        List<HorarioDisponibleRecurso> todosLosHorarios = horarioDisponibleRecursoRepository.findAll();

        // Identificar los horarios disponibles no utilizados
        List<HorarioDisponibleRecurso> horariosNoUtilizados = todosLosHorarios.stream()
                .filter(horario -> !horariosUtilizados.contains(horario))
                .collect(Collectors.toList());

        // Eliminar los horarios disponibles no utilizados
        horarioDisponibleRecursoRepository.deleteAll(horariosNoUtilizados);

        return recursoActualizadoSalida;
    }

    @PostConstruct
    private void configureMapping() {
        modelMapper.typeMap(RecursoEntradaDto.class, Recurso.class);
        modelMapper.typeMap(Recurso.class, RecursoSalidaDto.class);
        modelMapper.typeMap(RecursoModificacionDto.class,Recurso.class);
        modelMapper.typeMap(RecursoExternoEntradaDto.class,RecursoExterno.class);
        modelMapper.typeMap(RecursoExterno.class, RecursoExternoSalidaDto.class);
    }
}
