package com.cristian.ticket.business.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.cristian.ticket.business.domain.dto.TicketDto;
import com.cristian.ticket.business.domain.dto.base.TicketBaseDto;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface TicketBaseMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "fechaCreacion", ignore = true),
            @Mapping(target = "fechaActualizacion", ignore = true)
    })
    TicketDto toTicketDto(TicketBaseDto ticketBaseDto);

    TicketBaseDto toTicketBaseDto(TicketDto ticketDto);

}
