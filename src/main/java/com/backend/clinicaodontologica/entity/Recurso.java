package com.backend.clinicaodontologica.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recursos")
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String horaInicio;
    private String horaFin;
    private int tiempoMinimoReserva;
    private int tiempoMaximoReserva;

    @ElementCollection
    @CollectionTable(name = "dias_disponible_recurso")
    @Column(name = "dias_disponible_recurso")
    private List<String> diasDisponible;
}
