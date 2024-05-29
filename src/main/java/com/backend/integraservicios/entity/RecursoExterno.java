package com.backend.integraservicios.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recurso_externo")
public class RecursoExterno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String consecutivoRecurso;
    String codUnidad;
    String consecutivoTDR;
    String codHorario;
    String nombreRecurso;
    String estadoRecurso;
    String valorHora;
}
