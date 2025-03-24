package com.cristian.ticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.cristian.ticket.business.domain.dto.TicketDto;
import com.cristian.ticket.business.domain.dto.base.TicketBaseDto;
import com.cristian.ticket.business.domain.mapper.TicketBaseMapper;
import com.cristian.ticket.business.domain.mapper.TicketMapper;
import com.cristian.ticket.business.domain.persistence.entity.TicketEntity;
import com.cristian.ticket.business.domain.persistence.repository.TicketRepository;
import com.cristian.ticket.business.domain.service.impl.TicketServiceImpl;
import com.cristian.ticket.business.domain.util.Status;

@ExtendWith(MockitoExtension.class)
public class TicketTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @Mock
    private TicketBaseMapper ticketBaseMapper;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private TicketBaseDto ticketBaseDto;
    private TicketDto ticketDto;
    private TicketEntity ticketEntity;

    @BeforeEach
    void setUp() {
        ticketBaseDto = new TicketBaseDto();
        ticketBaseDto.setDescripcion("Problema con la red");

        ticketDto = new TicketDto();
        ticketDto.setId(1L);
        ticketDto.setDescripcion("Problema con la red");

        ticketEntity = new TicketEntity();
        ticketEntity.setId(1L);
        ticketEntity.setDescripcion("Problema con la red");
    }

    @Test
    void crearTicketExitosamente() {

        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticketEntity);

        TicketDto resultado = ticketService.crearTicket(any(Long.class),ticketBaseDto);

        assertNotNull(resultado);
        assertEquals("Problema con la red", resultado.getDescripcion());
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
    }

    @Test
    void crearTicketFalla() {

        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(null);

        TicketDto resultado = ticketService.crearTicket(any(Long.class),ticketBaseDto);

        assertNull(resultado);
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
    }

    @Test
    void actualizarTicketExitosamente() {

        TicketDto ticketDtoGuardado = new TicketDto();
        ticketDtoGuardado.setId(1L);
        ticketDtoGuardado.setFechaCreacion("2025-03-22");

        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticketEntity);

        TicketDto resultado = ticketService.actualizarTicket(any(Long.class),1L, ticketBaseDto, ticketDtoGuardado);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Problema con la red", resultado.getDescripcion());
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
    }

    @Test
    void obtenerTicketExiste() {

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticketEntity));

        TicketDto resultado = ticketService.obtenerTicket(1L);

        assertNotNull(resultado);
        assertEquals("Problema con la red", resultado.getDescripcion());
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerTicketNoExiste() {

        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        TicketDto resultado = ticketService.obtenerTicket(99L);

        assertNull(resultado);
        verify(ticketRepository, times(1)).findById(99L);
    }

    @Test
    void eliminarTicketEliminaExitosamente() {

        ticketService.eliminarTicket(any(Long.class),1L);

        verify(ticketRepository, times(1)).deleteById(1L);
    }

    @Test
    void obtenerTicketsExisten() {

        Page<TicketEntity> page = new PageImpl<>(List.of(ticketEntity));
        when(ticketRepository.findAll(any(Pageable.class))).thenReturn(page);

        List<TicketDto> resultado = ticketService.obtenerTickets(0, 5);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Problema con la red", resultado.get(0).getDescripcion());
        verify(ticketRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void obtenerTicketsNoExisten() {

        when(ticketRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        List<TicketDto> resultado = ticketService.obtenerTickets(0, 5);

        assertTrue(resultado.isEmpty());
        verify(ticketRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void obtenerPorUsuarioOStatusExisten() {

        when(ticketRepository.findByUsuarioIdOrStatus(1L, Status.ABIERTO)).thenReturn(List.of(ticketEntity));

        List<TicketDto> resultado = ticketService.obtenerPorUsuarioYStatus(1L, Status.ABIERTO);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(ticketRepository, times(1)).findByUsuarioIdOrStatus(1L, Status.ABIERTO);
    }
    
}
