package com.cristian.ticket.business.domain.dto;

import com.cristian.ticket.business.domain.dto.base.UsuarioBaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto extends UsuarioBaseDto {

    private Long id;

    private String fechaCreacion;

    private String fechaActualizacion;
}
