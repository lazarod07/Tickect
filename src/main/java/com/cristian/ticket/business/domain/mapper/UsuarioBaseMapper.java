package com.cristian.ticket.business.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.cristian.ticket.business.domain.dto.UsuarioDto;
import com.cristian.ticket.business.domain.dto.base.UsuarioBaseDto;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UsuarioBaseMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "fechaCreacion", ignore = true),
        @Mapping(target = "fechaActualizacion", ignore = true)
    })
    UsuarioDto toUsuarioDto(UsuarioBaseDto usuarioBaseDto);

    UsuarioBaseDto toUsuarioBaseDto(UsuarioDto usuarioDto);
}
