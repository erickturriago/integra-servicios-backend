package com.backend.integraservicios.repository;

import com.backend.integraservicios.entity.Reserva;
import com.backend.integraservicios.entity.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad, Long> {
}
