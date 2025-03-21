package com.cristian.ticket.business.domain.http.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.ticket.business.domain.dto.TicketDto;
import com.cristian.ticket.business.domain.dto.UsuarioDto;
import com.cristian.ticket.business.domain.dto.base.TicketBaseDto;
import com.cristian.ticket.business.domain.service.TicketService;
import com.cristian.ticket.business.domain.service.UsuarioService;
import com.cristian.ticket.business.domain.util.Status;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final UsuarioService usuarioService;

    public TicketController(TicketService ticketService, UsuarioService usuarioService) {
        this.ticketService = ticketService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Object> crearTicket(@RequestBody TicketBaseDto ticketBaseDto) {

        UsuarioDto usuarioDto = usuarioService.obtenerUsuario(ticketBaseDto.getUsuarioId());

        if (usuarioDto == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        TicketBaseDto ticketDto = ticketService.crearTicket(ticketBaseDto);

        return ticketDto != null ? ResponseEntity.ok().body(ticketDto)
                : ResponseEntity.badRequest().body("Parametros invalidos");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarTicket(@PathVariable(required = true) Long id,
            @RequestBody(required = true) TicketBaseDto ticketBaseDto) {

        TicketDto ticketDto = ticketService.obtenerTicket(id);

        if (ticketDto == null) {
            return ResponseEntity.status(404).body("Ticket no encontrado");
        }

        UsuarioDto usuarioDto = usuarioService.obtenerUsuario(ticketBaseDto.getUsuarioId());

        if (usuarioDto == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        ticketDto = ticketService.actualizarTicket(id, ticketBaseDto, ticketDto);

        return ticketDto != null ? ResponseEntity.ok().body(ticketDto)
                : ResponseEntity.badRequest().body("Parametros invalidos");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTicket(@PathVariable(required = true) Long id) {
        TicketDto ticketDto = ticketService.obtenerTicket(id);

        if (ticketDto == null) {
            return ResponseEntity.status(404).body("Ticket no encontrado");
        }

        ticketService.eliminarTicket(id);

        return ResponseEntity.ok().body("Ticket eliminado");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerTicket(@PathVariable(required = true) Long id) {
        TicketDto ticketDto = ticketService.obtenerTicket(id);

        return ticketDto != null ? ResponseEntity.ok().body(ticketDto)
                : ResponseEntity.status(404).body("Ticket no encontrado");
    }

    @GetMapping
    public ResponseEntity<Object> obtenerTickets(@RequestParam(defaultValue = "0", required = true) Integer pageNo,
            @RequestParam(defaultValue = "10", required = true) Integer size) {

        return ResponseEntity.ok().body(ticketService.obtenerTickets(pageNo, size));
    }

    @GetMapping("/filtro")
    public ResponseEntity<Object> obtenerPorUsuarioOStatus(@RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Status status) {

        return ResponseEntity.ok().body(ticketService.obtenerPorUsuarioOStatus(usuarioId, status));
    }

}
