package com.backend.integraservicios.repository;

import com.backend.integraservicios.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuario_Id(Long id);

    List<Reserva> findByRecurso_Id(Long id);
}
