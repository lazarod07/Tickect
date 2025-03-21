package com.cristian.ticket.business.domain.dto;

import com.cristian.ticket.business.domain.dto.base.TicketBaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDto extends TicketBaseDto {

    private Long id;

    private String fechaCreacion;

    private String fechaActualizacion;

}
