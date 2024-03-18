package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final ModelMapper modelMapper;
    private final PacienteService pacienteService;
    private OdontologoService odontologoService;


    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;

        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) throws BadRequestException {

        PacienteSalidaDto pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPaciente());

        OdontologoSalidaDto odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologo());

        Paciente paciente = null;
        Odontologo odontologo = null;

        if(pacienteBuscado != null){
            paciente = pacienteService.getModelMapper().map(pacienteBuscado,Paciente.class);
        }
        if(odontologoBuscado != null){
            odontologo = odontologoService.getModelMapper().map(odontologoBuscado,Odontologo.class);
        }

        Long idPacienteEntrada = turno.getPaciente();
        Long idOdontologoEntrada = turno.getOdontologo();

        TurnoSalidaDto turnoSalidaDto = null;

        LOGGER.info("TurnoEntradaDto: " + JsonPrinter.toString(turno));

        if (paciente != null && odontologo != null) {

            Turno turnoEntidad = modelMapper.map(turno,Turno.class);

            turnoEntidad.setOdontologo(odontologo);
            turnoEntidad.setPaciente(paciente);

            LOGGER.info("TurnoEntidad: " + JsonPrinter.toString(turnoEntidad));

            //mandamos a persistir a la capa dao y obtenemos una entidad
            Turno turnoAPersistir = turnoRepository.save(turnoEntidad);

            LOGGER.info("TurnoAPersistir: " + JsonPrinter.toString(turnoAPersistir));

            //transformamos la entidad obtenida en salidaDto
            turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);

            LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));


        } else {
            LOGGER.error("No fue posible insertar el turno");
            if(paciente==null && odontologo==null){
                LOGGER.info("No se ha encontrado paciente con ID "+idPacienteEntrada+ " ni odontologo con ID "+idOdontologoEntrada);
                throw new BadRequestException("No se ha encontrado paciente con ID "+idPacienteEntrada+ " ni odontologo con ID "+idOdontologoEntrada);
            }
            else if(paciente==null){
                LOGGER.info("No se ha encontrado paciente con ID "+idPacienteEntrada);
                throw new BadRequestException("No se ha encontrado paciente con ID "+idPacienteEntrada);
            }
            else if(odontologo==null){
                LOGGER.info("No se ha encontrado odontologo con ID "+idPacienteEntrada);
                throw new BadRequestException("No se ha encontrado odontologo con ID "+idOdontologoEntrada);
            }

        }


        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnoSalidaDtos = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();

        if (LOGGER.isInfoEnabled())
            LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnoSalidaDtos));
        return turnoSalidaDtos;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Paciente encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return turnoEncontrado;
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) {
        Turno turnoRecibido = modelMapper.map(turno, Turno.class);

        Turno turnoAActualizar = turnoRepository.findById(turnoRecibido.getId()).orElse(null);

        LOGGER.info("Turno A Actualizar: "+ JsonPrinter.toString(turnoAActualizar));

        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoAActualizar != null) {

            Paciente paciente = pacienteService.getModelMapper().map(pacienteService.buscarPacientePorId(turno.getPaciente()),Paciente.class);
            Odontologo odontologo = odontologoService.getModelMapper().map(odontologoService.buscarOdontologoPorId(turno.getOdontologo()),Odontologo.class);

            turnoAActualizar = turnoRecibido;

            if(paciente!= null && odontologo!=null){
                turnoAActualizar.setOdontologo(odontologo);
                turnoAActualizar.setPaciente(paciente);
            }

            turnoRepository.save(turnoAActualizar);

            turnoSalidaDto = modelMapper.map(turnoAActualizar, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado: {}", JsonPrinter.toString(turnoSalidaDto));

        } else {
            LOGGER.error("No fue posible actualizar el paciente porque no se encuentra en nuestra base de datos");
        }
        return turnoSalidaDto;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if (turnoRepository.findById(id).orElse(null) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el turno con id " + id);
        }
    }


    private void configureMapping() {
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(turno -> pacienteService.buscarPacientePorId(turno.getPaciente()), Turno::setPaciente))
                .addMappings(mapper -> mapper.map(turno -> odontologoService.buscarOdontologoPorId(turno.getOdontologo()), Turno::setOdontologo));
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(turno -> turno.getPaciente().getId(), TurnoSalidaDto::setPaciente))
                .addMappings(mapper -> mapper.map(turno -> turno.getOdontologo().getId(), TurnoSalidaDto::setOdontologo));
        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(turno -> turno.getPaciente(), Turno::setPaciente))
                .addMappings(mapper -> mapper.map(turno -> turno.getOdontologo(), Turno::setOdontologo));
    }
}
