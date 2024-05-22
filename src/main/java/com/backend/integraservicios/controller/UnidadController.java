package com.backend.integraservicios.controller;

import com.backend.integraservicios.dto.entrada.UnidadEntradaDto;
import com.backend.integraservicios.dto.modificacion.UnidadModificacionDto;
import com.backend.integraservicios.dto.salida.UnidadSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;
import com.backend.integraservicios.service.impl.UnidadService;
import com.backend.integraservicios.utils.JsonPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/unidades")
public class UnidadController {

    private final Logger LOGGER = LoggerFactory.getLogger(UnidadController.class);
    private final UnidadService unidadService;

    public UnidadController(UnidadService unidadService) {
        this.unidadService = unidadService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUnidad(@RequestBody UnidadEntradaDto unidad) throws BadRequestException {
        LOGGER.info("Unidad: "+ JsonPrinter.toString(unidad));
        return new ResponseEntity<>(unidadService.registrarUnidad(unidad), HttpStatus.OK);
        //return new ResponseEntity<>("Hecho", HttpStatus.OK);
    }

    @PutMapping("actualizar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UnidadSalidaDto> actualizarUnidad(@Valid @RequestBody UnidadModificacionDto unidad) throws ResourceNotFoundException,BadRequestException {
        return new ResponseEntity<>(unidadService.actualizarUnidad(unidad), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<UnidadSalidaDto>> listarUnidades() throws BadRequestException {
        return new ResponseEntity<>(unidadService.listarUnidades(), HttpStatus.OK);
        //return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ALIADO')")
    public ResponseEntity<UnidadSalidaDto> obtenerUnidadPorId(@PathVariable Long id) throws ResourceNotFoundException{
        return new ResponseEntity<>(unidadService.buscarUnidadPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> eliminarUnidad(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(unidadService.eliminarUnidad(id), HttpStatus.OK);
    }
}