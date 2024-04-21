package com.backend.clinicaodontologica.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recurso")
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagePath;

    @ManyToOne()
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    @ManyToMany(mappedBy = "recursos")
    public List<Reserva> reservas = new ArrayList<>();
}
