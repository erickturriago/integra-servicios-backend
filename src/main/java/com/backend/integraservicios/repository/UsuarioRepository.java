package com.backend.integraservicios.repository;

import com.backend.integraservicios.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
    @Query("select u from Usuario u where u.email = ?1 and u.contraseña = ?2")
    Usuario findByEmail(String email,String contraseña);

    List<Usuario> findByCedula(int cedula);
}
