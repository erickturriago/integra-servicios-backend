package com.backend.integraservicios.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unidad")
public class Unidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;
    private int tiempoMinimo;
    private int tiempoMaximo;
    private LocalDate horaFinal;
    @ManyToMany
    @JoinTable(
            name = "dias_disponibles_unidad",
            joinColumns = @JoinColumn(name = "unidad_id"),
            inverseJoinColumns = @JoinColumn(name = "dia_id")
    )
    private List<Dia> diasDisponibles;
}
