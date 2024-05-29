package com.backend.integraservicios.repository;

import com.backend.integraservicios.entity.Recurso;
import com.backend.integraservicios.entity.RecursoExterno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursoExternoRepository extends JpaRepository<RecursoExterno, Long> {
}
