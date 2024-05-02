package com.backend.integraservicios.service.impl;

import com.backend.integraservicios.dto.entrada.usuario.UsuarioEntradaDto;
import com.backend.integraservicios.dto.entrada.usuario.UsuarioLoginEntradaDto;
import com.backend.integraservicios.dto.modificacion.UsuarioModificacionEntradaDto;
import com.backend.integraservicios.dto.salida.reserva.ReservaSalidaDto;
import com.backend.integraservicios.dto.salida.usuario.UsuarioSalidaDto;
import com.backend.integraservicios.entity.Usuario;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;
import com.backend.integraservicios.repository.UsuarioRepository;
import com.backend.integraservicios.service.IUsuarioService;
import com.backend.integraservicios.utils.JsonPrinter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Service
public class UsuarioService implements IUsuarioService {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    private UsuarioRepository usuarioRepository;
    private ModelMapper modelMapper;

    @Override
    public Object registrarUsuario(UsuarioEntradaDto usuario) {
        //convertimos mediante el mapper de dtoEntrada a entidad
        LOGGER.info("UsuarioEntradaDTO: " + JsonPrinter.toString(usuario));

        List<Usuario> usuarioBuscado = usuarioRepository.findByCedula(usuario.getCedula());
        //Usuario usuarioBuscado = null;


        if(usuarioBuscado.size()>0){
            LOGGER.info("Usuario ya registrado");
            return "La cedula ya se encuentra registrado";
        }

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
        List<UsuarioSalidaDto> usuarios = usuarioRepository.findAll().stream()
                .map(u -> modelMapper.map(u, UsuarioSalidaDto.class))
                .collect(Collectors.toList());

        return usuarios;
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
    @PostConstruct
    private void configureMapping() {
        modelMapper.typeMap(UsuarioEntradaDto.class, Usuario.class);
        modelMapper.typeMap(Usuario.class, UsuarioSalidaDto.class);
        modelMapper.typeMap(UsuarioSalidaDto.class, Usuario.class);
        modelMapper.typeMap(UsuarioModificacionEntradaDto.class, Usuario.class);
    }
}
