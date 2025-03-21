package com.cristian.ticket.business.domain.service;

import java.util.List;

import com.cristian.ticket.business.domain.dto.UsuarioDto;
import com.cristian.ticket.business.domain.dto.base.UsuarioBaseDto;

public interface UsuarioService {

    UsuarioDto crearUsuario(UsuarioBaseDto usuarioBaseDto);

    UsuarioDto actualizarUsuario(Long id, UsuarioBaseDto usuarioBaseDto, UsuarioDto usuarioDto);

    UsuarioDto obtenerUsuario(Long id);

    List<UsuarioDto> obtenerUsuarios();
    
}
