package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;


    @Test
    @Order(1)
    void deberiaRegistrarUnOdontologoDeNombreJuanYRetornarElId(){
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("DOC1005996", "Juan", "Perez");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("Juan", odontologoSalidaDto.getNombre());
    }


    @Test
    @Order(2)
    void alIntentarEliminarUnOdontologoConId1YaEliminado_deberiaLanzarUnaResourceNotFoundException(){

        try{
            odontologoService.eliminarOdontologo(1L);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        assertThrows(ResourceNotFoundException.class, () -> odontologoService.eliminarOdontologo(1L));
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaVaciaDeOdontologos(){

        List<OdontologoSalidaDto> odontologosDto = odontologoService.listarOdontologos();

        assertTrue(odontologosDto.isEmpty());

    }
}
