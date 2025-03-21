package com.cristian.ticket.business.domain.service;

import java.util.List;

import com.cristian.ticket.business.domain.dto.TicketDto;
import com.cristian.ticket.business.domain.dto.base.TicketBaseDto;
import com.cristian.ticket.business.domain.util.Status;

public interface TicketService {

    TicketDto crearTicket(TicketBaseDto ticketBaseDto);

    TicketDto actualizarTicket(Long id, TicketBaseDto ticketBaseDto, TicketDto ticketDto);

    TicketDto obtenerTicket(Long id);

    void eliminarTicket(Long id);

    List<TicketDto> obtenerTickets(Integer pageNo, Integer pageSize);

    List<TicketDto> obtenerPorUsuarioOStatus(Long usuarioId, Status status);
    
}
