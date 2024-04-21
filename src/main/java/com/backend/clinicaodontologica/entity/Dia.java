package com.backend.clinicaodontologica.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dia")
public class Dia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "dias_disponibles_unidad",
            joinColumns = @JoinColumn(name = "id_dia"),
            inverseJoinColumns = @JoinColumn(name = "id_unidad")
    )
    private Set<Unidad> unidadesDisponibles;
}
