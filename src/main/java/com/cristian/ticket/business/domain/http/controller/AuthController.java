package com.cristian.ticket.business.domain.http.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.ticket.business.domain.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticación", description = "Operaciones relacionadas con la autenticación")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Crear token", description = "Crea un token jwt", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
    })
    @PostMapping("/token")
    public ResponseEntity<String> login(@Parameter(description = "Usuario creador del token", example = "cristian") @RequestParam String username) {

        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(token);
    }
}
