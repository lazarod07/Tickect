package com.cristian.ticket.business.domain.dto;

import com.cristian.ticket.business.domain.dto.base.TicketBaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Datos del ticket existente")
@Getter
@Setter
public class TicketDto extends TicketBaseDto {

    @Schema(description = "Identificador del ticket")
    private Long id;

    @Schema(description = "Fecha de creación del ticket")
    private String fechaCreacion;

    @Schema(description = "Fecha de actualización del ticket")
    private String fechaActualizacion;

}
