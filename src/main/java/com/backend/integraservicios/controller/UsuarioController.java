package com.backend.integraservicios.controller;

import com.backend.integraservicios.dto.entrada.usuario.UsuarioEntradaDto;
import com.backend.integraservicios.dto.entrada.usuario.UsuarioLoginEntradaDto;
import com.backend.integraservicios.dto.modificacion.UsuarioModificacionEntradaDto;
import com.backend.integraservicios.dto.salida.usuario.UsuarioSalidaDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioEntradaDto usuario) {
        LOGGER.info("Inicia registro");
        LOGGER.info("Usuario request: "+ JsonPrinter.toString(usuario));
        return new ResponseEntity<>(usuarioService.registrarUsuario(usuario), HttpStatus.OK);
    }

    //GET
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UsuarioSalidaDto> obtenerUsuarioPorId(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.buscarUsuarioPorId(id), HttpStatus.OK);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UsuarioSalidaDto>> listarUsuarios() {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    //PUT
    @PutMapping("/actualizar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UsuarioSalidaDto actualizarPaciente(@RequestBody UsuarioModificacionEntradaDto usuario) {
        return usuarioService.actualizarUsuario(usuario);
    }

    //DELETE
    @DeleteMapping("eliminar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
    }
}
