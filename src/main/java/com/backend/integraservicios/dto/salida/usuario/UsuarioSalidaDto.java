package com.backend.integraservicios.dto.salida.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSalidaDto {
    private int id;
    private String fullname;
    private String contraseña;
    private String email;
    private Integer cedula;
    private String fechaRegistro;
    private int rol;
}
