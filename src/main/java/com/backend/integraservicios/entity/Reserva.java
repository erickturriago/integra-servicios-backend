package com.backend.integraservicios.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idRecurso;
    private Long idUsuario;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String horaInicio;
    private String horaFin;
    private LocalDate fechaCreacion;
    private LocalDate fechaDevolucion;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "reserva_recurso",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "recurso_id")
    )
    private List<Recurso> recursos = new ArrayList<>();

}