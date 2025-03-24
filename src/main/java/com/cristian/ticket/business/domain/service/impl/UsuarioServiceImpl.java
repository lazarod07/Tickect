package com.cristian.ticket.business.domain.service.impl;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.cristian.ticket.business.domain.dto.UsuarioDto;
import com.cristian.ticket.business.domain.dto.base.UsuarioBaseDto;
import com.cristian.ticket.business.domain.mapper.UsuarioBaseMapper;
import com.cristian.ticket.business.domain.mapper.UsuarioMapper;
import com.cristian.ticket.business.domain.persistence.entity.UsuarioEntity;
import com.cristian.ticket.business.domain.persistence.repository.UsuarioRepository;
import com.cristian.ticket.business.domain.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioBaseMapper usuarioBaseMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = Mappers.getMapper(UsuarioMapper.class);
        this.usuarioBaseMapper = Mappers.getMapper(UsuarioBaseMapper.class);
    }

    @Override
    public UsuarioDto crearUsuario(UsuarioBaseDto usuarioBaseDto) {
        UsuarioDto usuarioDto = usuarioBaseMapper.toUsuarioDto(usuarioBaseDto);
        UsuarioEntity usuarioEntity = usuarioRepository.save(usuarioMapper.toUsuarioEntity(usuarioDto));
        return usuarioEntity != null ? usuarioMapper.toUsuarioDto(usuarioEntity) : null;
    }

    @Override
    public UsuarioDto actualizarUsuario(Long id, UsuarioBaseDto usuarioBaseDto, UsuarioDto usuarioDtoGuardado) {
        UsuarioDto usuarioDto = usuarioBaseMapper.toUsuarioDto(usuarioBaseDto);
        usuarioDto.setId(id);
        usuarioDto.setFechaCreacion(usuarioDtoGuardado.getFechaCreacion());
        UsuarioEntity usuarioEntity = usuarioRepository.save(usuarioMapper.toUsuarioEntity(usuarioDto));
        return usuarioEntity != null ? usuarioMapper.toUsuarioDto(usuarioEntity) : null;
    }

    @Override
    public UsuarioDto obtenerUsuario(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toUsuarioDto).orElse(null);
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return usuarioMapper.toUsuarioDtos(usuarioRepository.findAll());
    }

}
