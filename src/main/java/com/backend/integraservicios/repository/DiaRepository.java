package com.backend.integraservicios.repository;

import com.backend.integraservicios.entity.Dia;
import com.backend.integraservicios.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaRepository extends CrudRepository<Dia,Long> {
    @Query("select u from Usuario u where u.email = ?1 and u.contraseña = ?2")
    Usuario findByEmail(String email,String contraseña);
}
