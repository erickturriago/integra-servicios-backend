package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.usuario.UsuarioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.usuario.UsuarioLoginEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.UsuarioModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.usuario.UsuarioSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IUsuarioService {

    UsuarioSalidaDto registrarUsuario(UsuarioEntradaDto usuario);

    UsuarioSalidaDto iniciarSesion(UsuarioLoginEntradaDto usuario);

    List<UsuarioSalidaDto> listarUsuarios();

    UsuarioSalidaDto buscarUsuarioPorId(Long id);

    UsuarioSalidaDto actualizarUsuario(UsuarioModificacionEntradaDto usuario);

    void eliminarUsuario(Long id) throws ResourceNotFoundException;

    UsuarioSalidaDto buscarUsuarioPorEmail(int dni);
}
