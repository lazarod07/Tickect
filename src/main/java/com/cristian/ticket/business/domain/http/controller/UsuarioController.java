package com.cristian.ticket.business.domain.http.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.ticket.business.domain.dto.UsuarioDto;
import com.cristian.ticket.business.domain.dto.base.UsuarioBaseDto;
import com.cristian.ticket.business.domain.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

        private final UsuarioService usuarioService;

        public UsuarioController(UsuarioService usuarioService) {
                this.usuarioService = usuarioService;
        }

        @Operation(summary = "Crear usuario", description = "Crea un usuario apartir de la data suministrada", security = @SecurityRequirement(name = "bearer-jwt"), responses = {
                        @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class))),
                        @ApiResponse(responseCode = "400", description = "Parametros invalidos")
        })
        @PostMapping
        public ResponseEntity<Object> crearUsuario(
                        @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a crear", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioBaseDto.class))) @RequestBody(required = true) UsuarioBaseDto usuarioBaseDto) {

                UsuarioDto usuarioDto = usuarioService.crearUsuario(usuarioBaseDto);

                return usuarioDto != null ? ResponseEntity.status(201).body(usuarioDto)
                                : ResponseEntity.badRequest().body("Parametros invalidos");
        }

        @Operation(summary = "Actualiza usuario", description = "Actualiza un usuario apartir de la data suministrada", security = @SecurityRequirement(name = "bearer-jwt"), responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class))),
                        @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
        @PutMapping("/{id}")
        public ResponseEntity<Object> actualizarUsuario(
                        @Parameter(description = "Id del usuario a actualizar", example = "1") @PathVariable(required = true) Long id,
                        @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a actualizar", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioBaseDto.class))) @RequestBody(required = true) UsuarioBaseDto usuarioBaseDto) {

                UsuarioDto usuarioDto = usuarioService.obtenerUsuario(id);

                if (usuarioDto == null) {
                        return ResponseEntity.status(404).body("Usuario no encontrado");
                }

                usuarioDto = usuarioService.actualizarUsuario(id, usuarioBaseDto, usuarioDto);

                return usuarioDto != null ? ResponseEntity.ok().body(usuarioDto)
                                : ResponseEntity.badRequest().body("Parametros invalidos");
        }

        @Operation(summary = "Obtener usuarios", description = "Obtiene una lista de todos los usuarios existentes", security = @SecurityRequirement(name = "bearer-jwt"), responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UsuarioDto.class))))
        })
        @GetMapping
        public ResponseEntity<List<UsuarioDto>> obtenerUsuarios() {
                return ResponseEntity.ok().body(usuarioService.obtenerUsuarios());
        }

        @Operation(summary = "Obtener usuario", description = "Obtener un usuario apartir de su id", security = @SecurityRequirement(name = "bearer-jwt"), responses = {
                        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class))),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
        @GetMapping("/{id}")
        public ResponseEntity<Object> obtenerUsuario(
                        @Parameter(description = "Id del usuario a obtener", example = "1") @PathVariable(required = true) Long id) {

                UsuarioDto usuarioDto = usuarioService.obtenerUsuario(id);

                return usuarioDto != null ? ResponseEntity.ok().body(usuarioDto)
                                : ResponseEntity.status(404).body("Usuario no encontrado");
        }

}
