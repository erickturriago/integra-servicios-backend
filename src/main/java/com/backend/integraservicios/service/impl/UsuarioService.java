package com.backend.integraservicios.service.impl;

import com.backend.integraservicios.dto.entrada.UsuarioEntradaDto;
import com.backend.integraservicios.dto.entrada.UsuarioLoginEntradaDto;
import com.backend.integraservicios.dto.modificacion.UsuarioModificacionEntradaDto;
import com.backend.integraservicios.dto.salida.UsuarioSalidaDto;
import com.backend.integraservicios.entity.Usuario;
import com.backend.integraservicios.exceptions.BadRequestException;
import com.backend.integraservicios.exceptions.ResourceNotFoundException;
import com.backend.integraservicios.repository.UsuarioRepository;
import com.backend.integraservicios.service.IUsuarioService;
import com.backend.integraservicios.utils.JsonPrinter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Service
public class UsuarioService implements IUsuarioService {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    private UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    @Override
    public Object registrarUsuario(UsuarioEntradaDto usuario) throws BadRequestException{
        //convertimos mediante el mapper de dtoEntrada a entidad
        LOGGER.info("UsuarioEntradaDTO: " + JsonPrinter.toString(usuario));

        List<Usuario> usuarioBuscadoCedula = usuarioRepository.findByCedula(usuario.getCedula());
        Usuario usuarioBuscadoEmail = usuarioRepository.findOneByEmail(usuario.getEmail()).orElse(null);
        //Usuario usuarioBuscado = null;

        if(usuarioBuscadoEmail != null){
            LOGGER.info("Correo ya registrado");
            throw new BadRequestException("Correo ya registrado");
        }


        if(usuarioBuscadoCedula.size()>0){
            LOGGER.info("Usuario ya registrado");
            throw new BadRequestException("Cedula ya registrada");
        }

        Usuario usuarioEntidad = modelMapper.map(usuario, Usuario.class);
        LOGGER.info("Usuario Entidad Entrada: " + JsonPrinter.toString(usuarioEntidad));

        //Se encripta la contraseña
        usuarioEntidad.setContraseña(passwordEncoder.encode(usuarioEntidad.getContraseña()));

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


        Usuario usuarioABuscar = usuarioRepository.findByEmail(usuarioEntidad.getEmail(),usuarioEntidad.getContraseña());

        UsuarioSalidaDto usuarioSalidaDto;

        if(usuarioABuscar != null){
            LOGGER.info("Usuario existe");
            usuarioSalidaDto = modelMapper.map(usuarioABuscar, UsuarioSalidaDto.class);
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
    public UsuarioSalidaDto buscarUsuarioPorId(Long id) throws ResourceNotFoundException{
        Usuario usuarioBuscado = usuarioRepository.findById(id).orElse(null);

        UsuarioSalidaDto usuarioSalidaDto = null;
        if (usuarioBuscado != null) {
            usuarioSalidaDto = modelMapper.map(usuarioBuscado, UsuarioSalidaDto.class);
            LOGGER.info("Usuario encontrado: {}", usuarioSalidaDto);
        } else {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
            throw new ResourceNotFoundException("El id no se encuentra registrado en la base de datos");
        }

        return usuarioSalidaDto;
    }

    @Override
    public UsuarioSalidaDto buscarUsuarioPorEmail(String email) throws ResourceNotFoundException{
        Usuario usuarioBuscado = usuarioRepository.findOneByEmail(email).orElse(null);

        UsuarioSalidaDto usuarioSalidaDto = null;
        if (usuarioBuscado != null) {
            usuarioSalidaDto = modelMapper.map(usuarioBuscado, UsuarioSalidaDto.class);
            LOGGER.info("Usuario encontrado: {}", usuarioSalidaDto);
        } else {
            LOGGER.error("El email no se encuentra registrado en la base de datos");
            throw new ResourceNotFoundException("El email no se encuentra registrado en la base de datos");
        }

        return usuarioSalidaDto;
    }

    @Override
    public UsuarioSalidaDto actualizarUsuario(UsuarioModificacionEntradaDto usuario) throws ResourceNotFoundException,BadRequestException {
        Usuario usuarioAActualizar = modelMapper.map(buscarUsuarioPorId(usuario.getId()),Usuario.class);

        if(usuarioAActualizar==null){
            throw new ResourceNotFoundException("El usuario no existe");
        }

        List<Usuario> usuarioBuscadoCedula = usuarioRepository.findByCedula(usuario.getCedula());
        Usuario usuarioBuscadoEmail = usuarioRepository.findOneByEmail(usuario.getEmail()).orElse(null);


        if(!usuarioBuscadoCedula.isEmpty() && usuarioBuscadoCedula.get(0).getId()!= usuario.getId()){
            LOGGER.info("Usuario ya registrado");
            throw new BadRequestException("La cedula ya se encuentra registrado");
        }

        if(usuarioBuscadoEmail != null && usuarioBuscadoEmail.getId()!= usuario.getId()){
            LOGGER.info("Correo ya registrado");
            throw new BadRequestException("El correo ya se encuentra registrado");
        }

        usuarioAActualizar.setFullname(usuario.getFullname());
        usuarioAActualizar.setRol(usuario.getRol());
        usuarioAActualizar.setEmail(usuario.getEmail());
        usuarioAActualizar.setCedula(usuario.getCedula());

        return modelMapper.map(usuarioRepository.save(usuarioAActualizar),UsuarioSalidaDto.class);


    }

    @Override
    public UsuarioSalidaDto eliminarUsuario(Long id) throws ResourceNotFoundException {
        UsuarioSalidaDto usuarioAEliminar = null;
        usuarioAEliminar = buscarUsuarioPorId(id);
        if (usuarioAEliminar != null) {
            usuarioRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el usuario con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el usuario con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el recurso con id " + id);
        }
        return usuarioAEliminar;
    }
    @PostConstruct
    private void configureMapping() {
        modelMapper.typeMap(UsuarioEntradaDto.class, Usuario.class);
        modelMapper.typeMap(Usuario.class, UsuarioSalidaDto.class);
        modelMapper.typeMap(UsuarioSalidaDto.class, Usuario.class);
        modelMapper.typeMap(UsuarioModificacionEntradaDto.class, Usuario.class);
    }
}
