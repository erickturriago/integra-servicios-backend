package com.backend.integraservicios.service.impl;

import com.backend.integraservicios.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.integraservicios.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.integraservicios.dto.entrada.usuario.UsuarioEntradaDto;
import com.backend.integraservicios.dto.entrada.usuario.UsuarioLoginEntradaDto;
import com.backend.integraservicios.dto.salida.paciente.PacienteSalidaDto;
import com.backend.integraservicios.dto.salida.usuario.UsuarioSalidaDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Test
    @Order(1)
    void debeRegistrarUnUsuarioYRetornarSuInfoBasicaConId(){
        LocalDate date = LocalDate.of(2020, 1, 8);
        UsuarioEntradaDto usuarioEntradaDto = new UsuarioEntradaDto("Erick", "Turriago", "Ninguna123*","turriago-erick@hotmail.com",1005996807,date);

        UsuarioSalidaDto usuarioSalidaDto = usuarioService.registrarUsuario(usuarioEntradaDto);

        assertNotNull(usuarioSalidaDto.getId());
        assertEquals("Erick", usuarioSalidaDto.getNombre());
    }

    @Test
    @Order(2)
    void debeValidarQueUnUsuarioNoExisteAlIngresarMalLasCredenciales(){
        UsuarioLoginEntradaDto usuario = new UsuarioLoginEntradaDto("turriago-erick@hotmail.com","1234");
        //System.out.println(usuarioService.iniciarSesion(usuario));
        assertEquals(null,usuarioService.iniciarSesion(usuario));
    }

    @Test
    @Order(3)
    void debeValidarQueUnUsuarioExisteAlIngresarCredencialesValidas(){
        LocalDate date = LocalDate.of(2020, 1, 8);
        UsuarioEntradaDto usuarioEntradaDto = new UsuarioEntradaDto("Erick", "Turriago", "Ninguna123*","turriago-erick@hotmail.com",1005996807,date);
        UsuarioSalidaDto usuarioSalidaDto = usuarioService.registrarUsuario(usuarioEntradaDto);

        UsuarioLoginEntradaDto usuario = new UsuarioLoginEntradaDto("turriago-erick@hotmail.com","Ninguna123*");
        System.out.println(usuarioService.iniciarSesion(usuario));
        assertNotNull(usuarioService.iniciarSesion(usuario));
    }
}
