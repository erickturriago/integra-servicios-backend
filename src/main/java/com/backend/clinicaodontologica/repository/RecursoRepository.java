package com.backend.clinicaodontologica.repository;

import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
}
