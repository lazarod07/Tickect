package com.cristian.ticket.business.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cristian.ticket.business.domain.dto.UsuarioDto;
import com.cristian.ticket.business.domain.persistence.entity.UsuarioEntity;

@Mapper
public interface UsuarioMapper {

    UsuarioDto toUsuarioDto(UsuarioEntity usuarioEntity);

    UsuarioEntity toUsuarioEntity(UsuarioDto usuarioDto);

    List<UsuarioDto> toUsuarioDtos(List<UsuarioEntity> usuarioEntities);

    List<UsuarioEntity> toUsuarioEntities(List<UsuarioDto> usuarioDtos);
}
