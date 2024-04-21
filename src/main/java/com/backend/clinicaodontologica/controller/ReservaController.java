package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.recurso.RecursoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.reserva.ReservaEntradaDto;
import com.backend.clinicaodontologica.dto.salida.recurso.RecursoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.reserva.ReservaSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.service.impl.RecursoService;
import com.backend.clinicaodontologica.service.impl.ReservaService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final Logger LOGGER = LoggerFactory.getLogger(ReservaController.class);
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarReserva(@RequestBody ReservaEntradaDto reserva) throws BadRequestException {
        LOGGER.info("Reserva: "+ JsonPrinter.toString(reserva));
        return new ResponseEntity<>(reservaService.registrarReserva(reserva), HttpStatus.OK);
        //return new ResponseEntity<>("Hecho", HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ReservaSalidaDto>> listarRecursos() throws BadRequestException {
        return new ResponseEntity<>(reservaService.listarReservas(), HttpStatus.OK);
    }
}
