package com.backend.clinicaodontologica.repository;

import com.backend.clinicaodontologica.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
    @Query("select u from Usuario u where u.email = ?1 and u.contraseña = ?2")
    Usuario findByEmail(String email,String contraseña);
}
