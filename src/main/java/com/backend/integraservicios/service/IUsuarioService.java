package com.backend.integraservicios.service;

import com.backend.integraservicios.dto.entrada.usuario.UsuarioEntradaDto;
import com.backend.integraservicios.dto.entrada.usuario.UsuarioLoginEntradaDto;
import com.backend.integraservicios.dto.modificacion.UsuarioModificacionEntradaDto;
import com.backend.integraservicios.dto.salida.usuario.UsuarioSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IUsuarioService {

    Object registrarUsuario(UsuarioEntradaDto usuario);
    UsuarioSalidaDto iniciarSesion(UsuarioLoginEntradaDto usuario);
    List<UsuarioSalidaDto> listarUsuarios();
    UsuarioSalidaDto buscarUsuarioPorId(Long id) throws ResourceNotFoundException;
    UsuarioSalidaDto actualizarUsuario(UsuarioModificacionEntradaDto usuario);
    UsuarioSalidaDto eliminarUsuario(Long id) throws ResourceNotFoundException;
}
