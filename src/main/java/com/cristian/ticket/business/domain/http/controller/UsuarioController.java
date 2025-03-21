package com.cristian.ticket.business.domain.http.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.ticket.business.domain.dto.UsuarioDto;
import com.cristian.ticket.business.domain.dto.base.UsuarioBaseDto;
import com.cristian.ticket.business.domain.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Object> crearUsuario(@Valid @RequestBody(required = true) UsuarioBaseDto usuarioBaseDto) {

        UsuarioDto usuarioDto = usuarioService.crearUsuario(usuarioBaseDto);

        return usuarioDto != null ? ResponseEntity.ok().body(usuarioDto)
                : ResponseEntity.badRequest().body("Parametros invalidos");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarUsuario(@PathVariable(required = true) Long id,
            @Valid @RequestBody(required = true) UsuarioBaseDto usuarioBaseDto) {

        UsuarioDto usuarioDto = usuarioService.obtenerUsuario(id);

        if (usuarioDto == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        usuarioDto = usuarioService.actualizarUsuario(id, usuarioBaseDto, usuarioDto);

        return usuarioDto != null ? ResponseEntity.ok().body(usuarioDto)
                : ResponseEntity.badRequest().body("Parametros invalidos");
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> obtenerUsuarios() {
        return ResponseEntity.ok().body(usuarioService.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerUsuario(@PathVariable(required = true) Long id) {

        UsuarioDto usuarioDto = usuarioService.obtenerUsuario(id);

        return usuarioDto != null ? ResponseEntity.ok().body(usuarioDto)
                : ResponseEntity.status(404).body("Usuario no encontrado");
    }

}
