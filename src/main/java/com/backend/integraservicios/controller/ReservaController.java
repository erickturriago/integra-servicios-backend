package com.backend.integraservicios.controller;

import com.backend.integraservicios.dto.entrada.ReservaEntradaDto;
import com.backend.integraservicios.dto.modificacion.RecursoModificacionDto;
import com.backend.integraservicios.dto.modificacion.ReservaModificacionDto;
import com.backend.integraservicios.dto.salida.RecursoSalidaDto;
import com.backend.integraservicios.dto.salida.ReservaSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;
import com.backend.integraservicios.service.impl.ReservaService;
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
@RequestMapping("/reservas")
public class ReservaController {

    private final Logger LOGGER = LoggerFactory.getLogger(ReservaController.class);
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarReserva(@RequestBody ReservaEntradaDto reserva) throws BadRequestException {
        LOGGER.info("Reserva: "+ JsonPrinter.toString(reserva));
        return new ResponseEntity<>(reservaService.registrarReserva(reserva), HttpStatus.OK);
        //return new ResponseEntity<>("Hecho", HttpStatus.OK);
    }
    @PutMapping("/actualizar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ReservaSalidaDto> actualizarReserva(@Valid @RequestBody ReservaModificacionDto reservaModificacionDto) throws ResourceNotFoundException,BadRequestException {
        return new ResponseEntity<>(reservaService.actualizarReserva(reservaModificacionDto), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<ReservaSalidaDto>> listarRecursos() throws BadRequestException {
        return new ResponseEntity<>(reservaService.listarReservas(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ReservaSalidaDto> obtenerReservaPorId(@PathVariable Long id) throws ResourceNotFoundException{
        return new ResponseEntity<>(reservaService.buscarReservaPorId(id), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<ReservaSalidaDto>> listarReservasPorUsuario(@PathVariable Long id) throws BadRequestException {
        return new ResponseEntity<>(reservaService.listarReservasPorUsuario(id), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/recurso/{id}")
    public ResponseEntity<List<ReservaSalidaDto>> listarReservasPorRecurso(@PathVariable Long id) throws BadRequestException {
        return new ResponseEntity<>(reservaService.listarReservasPorRecurso(id), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(reservaService.cancelarReserva(id), HttpStatus.OK);
    }
}
