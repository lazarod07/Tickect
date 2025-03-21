package com.cristian.ticket.business.domain.dto.base;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioBaseDto implements Serializable{

    @NotBlank(message = "El campo nombres no puede estar vacio")
    @NotNull(message = "El campo nombres no puede ser nulo")
    private String nombres;

    @NotBlank(message = "El campo apellidos no puede estar vacio")
    @NotNull(message = "El campo apellidos no puede ser nulo")
    private String apellidos;

}
