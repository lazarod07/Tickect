package com.cristian.ticket.business.domain.dto.base;

import java.io.Serializable;

import com.cristian.ticket.business.domain.util.Status;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketBaseDto implements Serializable {

    @NotBlank(message = "El campo descripcion no puede estar vacio")
    @NotNull(message = "El campo descripcion no puede ser nulo")
    @Max(value = 500, message = "El campo descripcion no puede tener mas de 500 caracteres")
    private String descripcion;

    @NotBlank(message = "El campo usuarioId no puede estar vacio")
    @NotNull(message = "El campo usuarioId no puede ser nulo")
    private Long usuarioId;

    @NotBlank(message = "El campo status no puede estar vacio")
    @NotNull(message = "El campo status no puede ser nulo")
    private Status status;
    
}
