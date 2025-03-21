package com.cristian.ticket.business.domain.service.impl;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cristian.ticket.business.domain.dto.TicketDto;
import com.cristian.ticket.business.domain.dto.base.TicketBaseDto;
import com.cristian.ticket.business.domain.mapper.TicketBaseMapper;
import com.cristian.ticket.business.domain.mapper.TicketMapper;
import com.cristian.ticket.business.domain.persistence.entity.TicketEntity;
import com.cristian.ticket.business.domain.persistence.repository.TicketRepository;
import com.cristian.ticket.business.domain.service.TicketService;
import com.cristian.ticket.business.domain.util.Status;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final TicketBaseMapper ticketBaseMapper;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = Mappers.getMapper(TicketMapper.class);
        this.ticketBaseMapper = Mappers.getMapper(TicketBaseMapper.class);
    }

    @Override
    public TicketDto crearTicket(TicketBaseDto ticketBaseDto) {
        TicketDto ticketDto = ticketBaseMapper.toTicketDto(ticketBaseDto);
        TicketEntity ticketEntity = ticketRepository.save(ticketMapper.toTicketEntity(ticketDto));
        return ticketEntity != null ? ticketMapper.toTicketDto(ticketEntity) : null;
    }

    @Override
    public TicketDto actualizarTicket(Long id, TicketBaseDto ticketBaseDto, TicketDto ticketDtoGuardado) {
        TicketDto ticketDto = ticketBaseMapper.toTicketDto(ticketBaseDto);
        ticketDto.setId(id);
        ticketDto.setFechaCreacion(ticketDtoGuardado.getFechaCreacion());
        TicketEntity ticketEntity = ticketRepository.save(ticketMapper.toTicketEntity(ticketDto));
        return ticketEntity != null ? ticketMapper.toTicketDto(ticketEntity) : null;
    }

    @Override
    public TicketDto obtenerTicket(Long id) {
        return ticketRepository.findById(id).map(ticketMapper::toTicketDto).orElse(null);
    }

    @Override
    public void eliminarTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public List<TicketDto> obtenerTickets(Integer pageNo, Integer pageSize) {

        Page<TicketEntity> tickets = ticketRepository.findAll(Pageable.ofSize(pageSize).withPage(pageNo));

        return ticketMapper.toTicketDtos(tickets.getContent());

    }

    @Override
    public List<TicketDto> obtenerPorUsuarioOStatus(Long usuarioId, Status status) {
        return ticketMapper.toTicketDtos(ticketRepository.findByUsuarioIdOrStatus(usuarioId, status));
    }
    
}
