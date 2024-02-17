package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.utils.LocalDateTimeAdapter;
import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;
    private PacienteEntradaDto pacienteEntradaDto;
    private OdontologoEntradaDto odontologoEntradaDto;

    @Before
    public void setUp() {
        pacienteEntradaDto = new PacienteEntradaDto("Juan", "Perez", 123456789, LocalDate.of(2023, 12, 24), new DomicilioEntradaDto("calle", 1234, "Localidad", "Provincia"));
        odontologoEntradaDto = new OdontologoEntradaDto("DOC1005996","Andres","Perez");
    }


    @Test
    @Order(1)
    void alIntentarInsertarUnTurnoConUnPacienteYOdontologoQueNoExisten_deberiaLanazarUnaBadRequestException() throws BadRequestException {

        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(1L,1L,LocalDateTime.of(2024, 11, 20, 22, 20));

        try{
            turnoService.registrarTurno(turnoEntradaDto);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        assertThrows(BadRequestException.class, () -> turnoService.registrarTurno(turnoEntradaDto));
    }


    @Test
    @Order(2)
    void alIntentarEliminarUnTurnoConId1YaEliminado_deberiaLanzarUnaResourceNotFoundException(){

        try{
            turnoService.eliminarTurno(1L);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        assertThrows(ResourceNotFoundException.class, () -> turnoService.eliminarTurno(1L));
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaVaciaDeTurnos(){

        List<TurnoSalidaDto> turnosDto = turnoService.listarTurnos();

        assertTrue(turnosDto.isEmpty());

    }

}