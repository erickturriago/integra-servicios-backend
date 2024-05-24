package com.backend.integraservicios.service;

import com.backend.integraservicios.dto.entrada.UsuarioEntradaDto;
import com.backend.integraservicios.dto.entrada.UsuarioLoginEntradaDto;
import com.backend.integraservicios.dto.modificacion.UsuarioModificacionEntradaDto;
import com.backend.integraservicios.dto.salida.UsuarioSalidaDto;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IUsuarioService {

    Object registrarUsuario(UsuarioEntradaDto usuario) throws BadRequestException;
    UsuarioSalidaDto iniciarSesion(UsuarioLoginEntradaDto usuario);
    List<UsuarioSalidaDto> listarUsuarios();
    UsuarioSalidaDto buscarUsuarioPorId(Long id) throws ResourceNotFoundException;

    UsuarioSalidaDto buscarUsuarioPorEmail(String email) throws ResourceNotFoundException;
    UsuarioSalidaDto actualizarUsuario(UsuarioModificacionEntradaDto usuario) throws ResourceNotFoundException, BadRequestException;
    UsuarioSalidaDto eliminarUsuario(Long id) throws ResourceNotFoundException;
}
