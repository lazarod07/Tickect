package com.cristian.ticket;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cristian.ticket.business.domain.dto.TicketDto;
import com.cristian.ticket.business.domain.dto.UsuarioDto;
import com.cristian.ticket.business.domain.dto.base.TicketBaseDto;
import com.cristian.ticket.business.domain.http.controller.TicketController;
import com.cristian.ticket.business.domain.security.JwtUtil;
import com.cristian.ticket.business.domain.service.TicketService;
import com.cristian.ticket.business.domain.service.UsuarioService;
import com.cristian.ticket.business.domain.util.Status;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TicketController.class)
public class TicketIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketService ticketService;

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    void crearTicketExito() throws Exception {
        TicketBaseDto ticketBaseDto = new TicketBaseDto();
        ticketBaseDto.setUsuarioId(1L);
        ticketBaseDto.setDescripcion("Problemas de red");
        ticketBaseDto.setStatus(Status.ABIERTO);

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setNombres("Cristian David");
        usuarioDto.setApellidos("Zapata Lazaro");
        usuarioDto.setFechaCreacion("2021-08-01T00:00:00");
        usuarioDto.setFechaActualizacion("2021-08-01T00:00:00");

        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(1L);
        ticketDto.setDescripcion("Problemas de red");
        ticketDto.setStatus(Status.ABIERTO);
        ticketDto.setUsuarioId(1L);
        ticketDto.setFechaCreacion("2021-08-01T00:00:00");
        ticketDto.setFechaActualizacion("2021-08-01T00:00:00");

        when(usuarioService.obtenerUsuario(1L)).thenReturn(usuarioDto);
        when(ticketService.crearTicket(any(Long.class), any(TicketBaseDto.class))).thenReturn(ticketDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(ticketBaseDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Problemas de red"))
                .andExpect(jsonPath("$.status").value("ABIERTO"))
                .andExpect(jsonPath("$.usuarioId").value(1L));
    }

    @Test
    void crearTicketUsuarioNoExiste() throws Exception {
        TicketBaseDto ticketBaseDto = new TicketBaseDto();
        ticketBaseDto.setUsuarioId(1L);
        ticketBaseDto.setDescripcion("Problemas de red");
        ticketBaseDto.setStatus(Status.ABIERTO);

        when(usuarioService.obtenerUsuario(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketBaseDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    void actualizarTicketExito() throws Exception {
        TicketBaseDto ticketBaseDto = new TicketBaseDto();
        ticketBaseDto.setUsuarioId(1L);
        ticketBaseDto.setDescripcion("Problemas de red");
        ticketBaseDto.setStatus(Status.ABIERTO);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(1L);
        ticketDto.setDescripcion("Problemas de red");
        ticketDto.setStatus(Status.ABIERTO);
        ticketDto.setUsuarioId(1L);
        ticketDto.setFechaCreacion("2021-08-01T00:00:00");
        ticketDto.setFechaActualizacion("2021-08-01T00:00:00");

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setNombres("Cristian David");
        usuarioDto.setApellidos("Zapata Lazaro");
        usuarioDto.setFechaCreacion("2021-08-01T00:00:00");
        usuarioDto.setFechaActualizacion("2021-08-01T00:00:00");

        when(ticketService.obtenerTicket(1L)).thenReturn(ticketDto);
        when(usuarioService.obtenerUsuario(1L)).thenReturn(usuarioDto);
        when(ticketService.actualizarTicket(any(Long.class), eq(1L), any(TicketBaseDto.class), any(TicketDto.class)))
                .thenReturn(ticketDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/tickets/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(ticketBaseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Problemas de red"))
                .andExpect(jsonPath("$.status").value("ABIERTO"))
                .andExpect(jsonPath("$.usuarioId").value(1L));
    }

    @Test
    void eliminarTicketExito() throws Exception {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(1L);
        ticketDto.setUsuarioId(1L);

        when(ticketService.obtenerTicket(1L)).thenReturn(ticketDto);
        doNothing().when(ticketService).eliminarTicket(1L, 1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket eliminado"));
    }

    @Test
    void eliminarTicketNoExiste() throws Exception {
        when(ticketService.obtenerTicket(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Ticket no encontrado"));
    }

    @Test
    void obtenerTicketExito() throws Exception {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(1L);

        when(ticketService.obtenerTicket(1L)).thenReturn(ticketDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtenerTicketsNoExisten() throws Exception {
        when(ticketService.obtenerTickets(anyInt(), anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets?pageNo=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void obtenerPorUsuarioYStatusExito() throws Exception {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(1L);
        ticketDto.setDescripcion("Problemas de red");
        ticketDto.setStatus(Status.ABIERTO);
        ticketDto.setUsuarioId(1L);
        ticketDto.setFechaCreacion("2021-08-01T00:00:00");
        ticketDto.setFechaActualizacion("2021-08-01T00:00:00");

        when(ticketService.obtenerPorUsuarioYStatus(1L, Status.ABIERTO)).thenReturn(List.of(ticketDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/filtro?usuarioId=1&status=ABIERTO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].descripcion").value("Problemas de red"))
                .andExpect(jsonPath("$[0].status").value("ABIERTO"))
                .andExpect(jsonPath("$[0].usuarioId").value(1L));
    }

    @Test
    void obtenerPorUsuarioYStatusExitoCache() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/filtro?usuarioId=1&status=ABIERTO"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/filtro?usuarioId=1&status=ABIERTO"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
