package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.recurso.RecursoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.service.impl.OdontologoService;
import com.backend.clinicaodontologica.service.impl.RecursoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recursos")
public class RecursoController {

    private final Logger LOGGER = LoggerFactory.getLogger(RecursoController.class);
    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarRecurso(@RequestBody RecursoEntradaDto recurso) throws BadRequestException {
        LOGGER.info("Recurso: "+ JsonPrinter.toString(recurso));
        return new ResponseEntity<>(recursoService.registrarRecurso(recurso), HttpStatus.OK);
        //return new ResponseEntity<>("Hecho", HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RecursoSalidaDto>> listarRecursos() throws BadRequestException {
        return new ResponseEntity<>(recursoService.listarRecursos(), HttpStatus.OK);
    }
}
