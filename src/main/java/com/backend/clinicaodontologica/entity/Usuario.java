package com.backend.clinicaodontologica.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nombre;
    @Column(length = 50)
    private String apellido;
    @Column(length = 50)
    private String contraseña;
    @Column(length = 50)
    private int cedula;
    private LocalDate fechaRegistro;
}
