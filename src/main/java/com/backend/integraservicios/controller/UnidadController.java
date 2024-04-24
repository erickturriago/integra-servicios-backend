package com.backend.integraservicios.controller;

import com.backend.integraservicios.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.integraservicios.dto.entrada.unidad.UnidadEntradaDto;
import com.backend.integraservicios.dto.salida.recurso.RecursoSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.service.impl.RecursoService;
import com.backend.integraservicios.service.impl.UnidadService;
import com.backend.integraservicios.utils.JsonPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades")
public class UnidadController {

    private final Logger LOGGER = LoggerFactory.getLogger(UnidadController.class);
    private final UnidadService unidadService;

    public UnidadController(UnidadService unidadService) {
        this.unidadService = unidadService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUnidad(@RequestBody UnidadEntradaDto unidad) throws BadRequestException {
        LOGGER.info("Unidad: "+ JsonPrinter.toString(unidad));
        return new ResponseEntity<>(unidadService.registrarUnidad(unidad), HttpStatus.OK);
        //return new ResponseEntity<>("Hecho", HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RecursoSalidaDto>> listarRecursos() throws BadRequestException {
        //return new ResponseEntity<>(recursoService.listarRecursos(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}