package com.cristian.ticket.business.domain.dto;

import com.cristian.ticket.business.domain.dto.base.UsuarioBaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Datos de usuario existente")
@Getter
@Setter
public class UsuarioDto extends UsuarioBaseDto {

    @Schema(description = "Identificador del usuario")
    private Long id;

    @Schema(description = "Fecha de creación del usuario")
    private String fechaCreacion;

    @Schema(description = "Fecha de actualización del usuario")
    private String fechaActualizacion;
}
