package com.backend.integraservicios.controller;

import com.backend.integraservicios.dto.entrada.UsuarioEntradaDto;
import com.backend.integraservicios.dto.modificacion.UsuarioModificacionEntradaDto;
import com.backend.integraservicios.dto.salida.UsuarioSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;
import com.backend.integraservicios.service.IUsuarioService;
import com.backend.integraservicios.service.impl.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {
    private IUsuarioService usuarioService;
    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping("/registrar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioEntradaDto usuario) throws BadRequestException{
        LOGGER.info("Inicia registro");
        LOGGER.info("Usuario request: "+ JsonPrinter.toString(usuario));
        return new ResponseEntity<>(usuarioService.registrarUsuario(usuario), HttpStatus.OK);
    }

    @PutMapping("actualizar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UsuarioSalidaDto> actualizarUsuario(@Valid @RequestBody UsuarioModificacionEntradaDto usuario) throws ResourceNotFoundException, BadRequestException {
        return new ResponseEntity<>(usuarioService.actualizarUsuario(usuario), HttpStatus.OK);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UsuarioSalidaDto>> listarUsuarios() {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UsuarioSalidaDto> obtenerUsuarioPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.buscarUsuarioPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.eliminarUsuario(id), HttpStatus.OK);
    }


}
