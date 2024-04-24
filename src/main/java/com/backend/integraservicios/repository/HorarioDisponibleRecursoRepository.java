package com.backend.integraservicios.repository;

import com.backend.integraservicios.entity.HorarioDisponibleRecurso;
import com.backend.integraservicios.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioDisponibleRecursoRepository extends JpaRepository<HorarioDisponibleRecurso, Long> {
}
