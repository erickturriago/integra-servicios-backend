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

    //POST
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioEntradaDto usuario) {
        LOGGER.info("Inicia registro");
        LOGGER.info("Usuario request: "+ JsonPrinter.toString(usuario));
        return new ResponseEntity<>(usuarioService.registrarUsuario(usuario), HttpStatus.OK);
    }

    @PostMapping("/iniciarSesion")
    public ResponseEntity<?> iniciarSesion(@Valid @RequestBody UsuarioLoginEntradaDto usuario) {
        LOGGER.info("Inicia registro");
        LOGGER.info("Usuario request: "+ JsonPrinter.toString(usuario));
        UsuarioSalidaDto usuarioSalida = usuarioService.iniciarSesion(usuario);

        if(usuarioSalida!=null){
            return new ResponseEntity<>(usuarioSalida, HttpStatus.OK);
        }
        else{
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("error", "Usuario no existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }


    //GET
    @GetMapping("{id}")
    public ResponseEntity<UsuarioSalidaDto> obtenerUsuarioPorId(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.buscarUsuarioPorId(id), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioSalidaDto>> listarUsuarios() {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    //PUT
    @PutMapping("/actualizar")
    public UsuarioSalidaDto actualizarPaciente(@RequestBody UsuarioModificacionEntradaDto usuario) {
        return usuarioService.actualizarUsuario(usuario);
    }

    //DELETE
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
    }
}
