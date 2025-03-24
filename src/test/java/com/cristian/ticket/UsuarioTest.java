package com.cristian.ticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cristian.ticket.business.domain.dto.UsuarioDto;
import com.cristian.ticket.business.domain.dto.base.UsuarioBaseDto;
import com.cristian.ticket.business.domain.mapper.UsuarioBaseMapper;
import com.cristian.ticket.business.domain.mapper.UsuarioMapper;
import com.cristian.ticket.business.domain.persistence.entity.UsuarioEntity;
import com.cristian.ticket.business.domain.persistence.repository.UsuarioRepository;
import com.cristian.ticket.business.domain.service.impl.UsuarioServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UsuarioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private UsuarioBaseMapper usuarioBaseMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private UsuarioBaseDto usuarioBaseDto;
    private UsuarioDto usuarioDto;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        usuarioBaseDto = new UsuarioBaseDto();
        usuarioBaseDto.setNombres("Cristian Zapata");

        usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setNombres("Cristian Zapata");

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1L);
        usuarioEntity.setNombres("Cristian Zapata");
    }

    @Test
    void crearUsuarioExitosamente() {

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        UsuarioDto resultado = usuarioService.crearUsuario(usuarioBaseDto);

        assertNotNull(resultado);
        assertEquals("Cristian Zapata", resultado.getNombres());
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void crearUsuarioFalla() {

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(null);

        UsuarioDto resultado = usuarioService.crearUsuario(usuarioBaseDto);

        assertNull(resultado);
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void actualizarUsuarioExitosamente() {

        UsuarioDto usuarioDtoGuardado = new UsuarioDto();
        usuarioDtoGuardado.setId(1L);
        usuarioDtoGuardado.setFechaCreacion("2025-03-22");

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        UsuarioDto resultado = usuarioService.actualizarUsuario(1L, usuarioBaseDto, usuarioDtoGuardado);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Cristian Zapata", resultado.getNombres());
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void obtenerUsuarioExiste() {

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));

        UsuarioDto resultado = usuarioService.obtenerUsuario(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Cristian Zapata", resultado.getNombres());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerUsuarioNoExiste() {

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        UsuarioDto resultado = usuarioService.obtenerUsuario(99L);

        assertNull(resultado);
        verify(usuarioRepository, times(1)).findById(99L);
    }

    @Test
    void obtenerUsuariosExisten() {

        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioEntity));

        List<UsuarioDto> resultado = usuarioService.obtenerUsuarios();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Cristian Zapata", resultado.get(0).getNombres());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void obtenerUsuariosNoExisten() {

        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<UsuarioDto> resultado = usuarioService.obtenerUsuarios();

        assertTrue(resultado.isEmpty());
        verify(usuarioRepository, times(1)).findAll();
    }

}
