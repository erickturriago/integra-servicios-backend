package com.backend.integraservicios.controller;

import com.backend.integraservicios.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.integraservicios.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.integraservicios.dto.modificacion.RecursoModificacionDto;
import com.backend.integraservicios.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.integraservicios.dto.salida.recurso.RecursoSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;
import com.backend.integraservicios.service.impl.RecursoService;
import com.backend.integraservicios.utils.JsonPrinter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recursos")
@CrossOrigin
public class RecursoController {

    private final Logger LOGGER = LoggerFactory.getLogger(RecursoController.class);
    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> registrarRecurso(@RequestBody RecursoEntradaDto recurso) throws BadRequestException {
        LOGGER.info("Recurso: "+ JsonPrinter.toString(recurso));
        return new ResponseEntity<>(recursoService.registrarRecurso(recurso), HttpStatus.OK);
        //return new ResponseEntity<>("Hecho", HttpStatus.OK);
    }

    @PutMapping("actualizar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RecursoSalidaDto> actualizarRecurso(@Valid @RequestBody RecursoModificacionDto recurso) throws ResourceNotFoundException,BadRequestException {
        return new ResponseEntity<>(recursoService.actualizarRecurso(recurso), HttpStatus.OK);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ALIADO')")
    public ResponseEntity<List<RecursoSalidaDto>> listarRecursos() throws BadRequestException {
        LOGGER.info("Inicia endpoint listar recursos");
        return new ResponseEntity<>(recursoService.listarRecursos(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','ALIADO')")
    public ResponseEntity<RecursoSalidaDto> obtenerRecursoPorId(@PathVariable Long id) throws ResourceNotFoundException{
        return new ResponseEntity<>(recursoService.buscarRecursoPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> eliminarRecurso(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(recursoService.eliminarRecurso(id), HttpStatus.OK);
    }
}