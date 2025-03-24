package com.cristian.ticket.business.domain.service;

import java.util.List;

import com.cristian.ticket.business.domain.dto.TicketDto;
import com.cristian.ticket.business.domain.dto.base.TicketBaseDto;
import com.cristian.ticket.business.domain.util.Status;

public interface TicketService {

    TicketDto crearTicket(Long usuarioId, TicketBaseDto ticketBaseDto);

    TicketDto actualizarTicket(Long usuarioId, Long id, TicketBaseDto ticketBaseDto, TicketDto ticketDto);

    TicketDto obtenerTicket(Long id);

    void eliminarTicket(Long usuarioId, Long id);

    List<TicketDto> obtenerTickets(Integer pageNo, Integer pageSize);

    List<TicketDto> obtenerPorUsuarioYStatus(Long usuarioId, Status status);
    
}
