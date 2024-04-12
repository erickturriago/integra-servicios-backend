package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.recurso.RecursoEntradaDto;
<<<<<<< HEAD
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
=======
import com.backend.clinicaodontologica.dto.salida.recurso.RecursoSalidaDto;
>>>>>>> fd5e0f20ebc98e33f0375b20aab2623dcbf62b5c
import com.backend.clinicaodontologica.exceptions.BadRequestException;

import java.util.List;

public interface IRecursoService {

    Long registrarRecurso(RecursoEntradaDto recurso) throws BadRequestException;

<<<<<<< HEAD
    //List<RecursoS> listarPacientes();
=======
    List<RecursoSalidaDto> listarRecursos() throws BadRequestException;
>>>>>>> fd5e0f20ebc98e33f0375b20aab2623dcbf62b5c

    //TurnoSalidaDto buscarTurnoPorId(Long id);

    //TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno);

    //void eliminarTurno(Long id) throws ResourceNotFoundException;


}
