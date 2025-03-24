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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Tickets", description = "Operaciones relacionadas con tickets")
@RestController
@RequestMapping("/tickets")
public class TicketController {

        private final TicketService ticketService;
        private final UsuarioService usuarioService;

        public TicketController(TicketService ticketService, UsuarioService usuarioService) {
                this.ticketService = ticketService;
                this.usuarioService = usuarioService;
        }

        @Operation(summary = "Crear ticket", description = "Crea un ticket apartir de la data suministrada", responses = {
                        @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDto.class))),
                        @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
        @PostMapping
        public ResponseEntity<Object> crearTicket(
                        @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del ticket a crear", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketBaseDto.class))) @RequestBody TicketBaseDto ticketBaseDto) {

                UsuarioDto usuarioDto = usuarioService.obtenerUsuario(ticketBaseDto.getUsuarioId());

                if (usuarioDto == null) {
                        return ResponseEntity.status(404).body("Usuario no encontrado");
                }

                TicketDto ticketDto = ticketService.crearTicket(usuarioDto.getId(), ticketBaseDto);

                return ticketDto != null ? ResponseEntity.status(201).body(ticketDto)
                                : ResponseEntity.badRequest().body("Parametros invalidos");
        }

        @Operation(summary = "Actualizar ticket", description = "Actualizar un ticket apartir de la data suministrada", responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDto.class))),
                        @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
        })
        @PutMapping("/{id}")
        public ResponseEntity<Object> actualizarTicket(
                        @Parameter(description = "Id del ticket a actualizar", example = "1") @PathVariable(required = true) Long id,
                        @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del ticket a actualizar", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketBaseDto.class))) @RequestBody(required = true) TicketBaseDto ticketBaseDto) {

                TicketDto ticketDto = ticketService.obtenerTicket(id);

                if (ticketDto == null) {
                        return ResponseEntity.status(404).body("Ticket no encontrado");
                }

                UsuarioDto usuarioDto = usuarioService.obtenerUsuario(ticketBaseDto.getUsuarioId());

                if (usuarioDto == null) {
                        return ResponseEntity.status(404).body("Usuario no encontrado");
                }

                ticketDto = ticketService.actualizarTicket(usuarioDto.getId(), id, ticketBaseDto, ticketDto);

                return ticketDto != null ? ResponseEntity.ok().body(ticketDto)
                                : ResponseEntity.badRequest().body("Parametros invalidos");
        }

        @Operation(summary = "Crear ticket", description = "Crea un ticket apartir de la data suministrada", responses = {
                        @ApiResponse(responseCode = "200", description = "Ticket eliminado"),
                        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminarTicket(
                        @Parameter(description = "Id del ticker a eliminar", example = "1") @PathVariable(required = true) Long id) {
                TicketDto ticketDto = ticketService.obtenerTicket(id);

                if (ticketDto == null) {
                        return ResponseEntity.status(404).body("Ticket no encontrado");
                }

                ticketService.eliminarTicket(ticketDto.getUsuarioId(), id);

                return ResponseEntity.ok().body("Ticket eliminado");
        }

        @Operation(summary = "Obtener ticket", description = "Obtener un ticket apartir de su id", responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDto.class))),
                        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
        })
        @GetMapping("/{id}")
        public ResponseEntity<Object> obtenerTicket(
                        @Parameter(description = "Id del ticket a obtener", example = "1") @PathVariable(required = true) Long id) {
                TicketDto ticketDto = ticketService.obtenerTicket(id);

                return ticketDto != null ? ResponseEntity.ok().body(ticketDto)
                                : ResponseEntity.status(404).body("Ticket no encontrado");
        }

        @Operation(summary = "Obtener tickets", description = "Obtiene una lista paginada de tickers existentes", responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TicketDto.class))))
        })
        @GetMapping
        public ResponseEntity<Object> obtenerTickets(
                        @Parameter(description = "Número de la pagina a obtener", example = "0") @RequestParam(defaultValue = "0", required = true) Integer pageNo,
                        @Parameter(description = "Número de tickets por pagina a obtener", example = "10") @RequestParam(defaultValue = "10", required = true) Integer size) {

                return ResponseEntity.ok().body(ticketService.obtenerTickets(pageNo, size));
        }

        @Operation(summary = "Obtener tickets", description = "Obtiene una lista de tickers existentes dado el id del usuario o el status", responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TicketDto.class))))
        })
        @GetMapping("/filtro")
        public ResponseEntity<Object> obtenerPorUsuarioOStatus(
                        @Parameter(description = "Id del usuario a filtrar", example = "1") @RequestParam(required = true) Long usuarioId,
                        @Parameter(description = "Status a filtrar", example = "ABIERTO") @RequestParam(required = true) Status status) {

                return ResponseEntity.ok().body(ticketService.obtenerPorUsuarioYStatus(usuarioId, status));
        }

}
