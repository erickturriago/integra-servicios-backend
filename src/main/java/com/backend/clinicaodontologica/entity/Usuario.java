package com.backend.clinicaodontologica.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String apellido;

    @NotBlank
    private String contraseña;

    @Email
    @NotBlank
    @Size(max=80)
    private String email;

    @Column(length = 50)
    private int cedula;
    private LocalDate fechaRegistro;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String contraseña, String email, int cedula, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.email = email;
        this.cedula = cedula;
        this.fechaRegistro = fechaRegistro;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    //@ManyToMany(fetch = FetchType.EAGER, targetEntity = Rol.class,cascade = CascadeType.PERSIST)
    //@JoinTable(name = "usuarios_roles",joinColumns = @JoinColumn(name="usuario_id"),inverseJoinColumns = @JoinColumn(name = "rol_id"))
    //private Set<Rol> roles;
}
