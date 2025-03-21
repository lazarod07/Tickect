package com.cristian.ticket.business.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cristian.ticket.business.domain.dto.TicketDto;
import com.cristian.ticket.business.domain.persistence.entity.TicketEntity;

@Mapper
public interface TicketMapper {

    TicketDto toTicketDto(TicketEntity ticketEntity);

    TicketEntity toTicketEntity(TicketDto ticketDto);

    List<TicketDto> toTicketDtos(List<TicketEntity> ticketEntities);

    List<TicketEntity> toTicketEntities(List<TicketDto> ticketDtos);

}
