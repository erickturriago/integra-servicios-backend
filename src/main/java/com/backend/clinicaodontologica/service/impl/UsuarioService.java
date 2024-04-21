package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.usuario.UsuarioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.usuario.UsuarioLoginEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.UsuarioModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.usuario.UsuarioSalidaDto;
import com.backend.clinicaodontologica.entity.Usuario;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.UsuarioRepository;
import com.backend.clinicaodontologica.service.IUsuarioService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioService implements IUsuarioService {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    private UsuarioRepository usuarioRepository;
    private ModelMapper modelMapper;
    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public UsuarioSalidaDto registrarUsuario(UsuarioEntradaDto usuario) {
        //convertimos mediante el mapper de dtoEntrada a entidad
        LOGGER.info("UsuarioEntradaDTO: " + JsonPrinter.toString(usuario));

        Usuario usuarioEntidad = modelMapper.map(usuario, Usuario.class);
        LOGGER.info("Usuario Entidad Entrada: " + JsonPrinter.toString(usuarioEntidad));

        //mandamos a persistir a la capa dao y obtenemos una entidad
        Usuario usuarioAPersistir = usuarioRepository.save(usuarioEntidad);
        LOGGER.info("Usuario Entidad Guardado: " + JsonPrinter.toString(usuarioAPersistir));

        //transformamos la entidad obtenida en salidaDto
        UsuarioSalidaDto usuarioSalidaDto = modelMapper.map(usuarioAPersistir, UsuarioSalidaDto.class);
        LOGGER.info("UsuarioSalidaDto: " + JsonPrinter.toString(usuarioSalidaDto));
        return usuarioSalidaDto;
    }

    @Override
    public UsuarioSalidaDto iniciarSesion(UsuarioLoginEntradaDto usuario) {

        LOGGER.info("UsuarioLoginEntradaDto: " + JsonPrinter.toString(usuario));

        Usuario usuarioEntidad = modelMapper.map(usuario, Usuario.class);


        Usuario usuarioAPersistir = usuarioRepository.findByEmail(usuarioEntidad.getEmail(),usuarioEntidad.getContraseña());

        UsuarioSalidaDto usuarioSalidaDto;

        if(usuarioAPersistir != null){
            LOGGER.info("Usuario existe");
            usuarioSalidaDto = modelMapper.map(usuarioAPersistir, UsuarioSalidaDto.class);
            LOGGER.info("UsuarioSalidaDto: " + JsonPrinter.toString(usuarioSalidaDto));
            return usuarioSalidaDto;
        }
        else{
            LOGGER.info("Usuario no existe ");
            return null;
        }
    }

    @Override
    public List<UsuarioSalidaDto> listarUsuarios() {
        return null;
    }

    @Override
    public UsuarioSalidaDto buscarUsuarioPorId(Long id) {
        return null;
    }

    @Override
    public UsuarioSalidaDto actualizarUsuario(UsuarioModificacionEntradaDto usuario) {
        return null;
    }

    @Override
    public void eliminarUsuario(Long id) throws ResourceNotFoundException {

    }

    @Override
    public UsuarioSalidaDto buscarUsuarioPorEmail(int dni) {
        return null;
    }

    private void configureMapping() {
        modelMapper.typeMap(UsuarioEntradaDto.class, Usuario.class);
        modelMapper.typeMap(Usuario.class, UsuarioSalidaDto.class);
        modelMapper.typeMap(UsuarioSalidaDto.class, Usuario.class);
        modelMapper.typeMap(UsuarioModificacionEntradaDto.class, Usuario.class);
    }
}
