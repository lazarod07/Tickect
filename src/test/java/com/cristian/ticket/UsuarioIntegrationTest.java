package com.cristian.ticket;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cristian.ticket.business.domain.dto.UsuarioDto;
import com.cristian.ticket.business.domain.dto.base.UsuarioBaseDto;
import com.cristian.ticket.business.domain.http.controller.UsuarioController;
import com.cristian.ticket.business.domain.security.JwtUtil;
import com.cristian.ticket.business.domain.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UsuarioController.class)
public class UsuarioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    void crearUsuarioExito() throws Exception {

        UsuarioBaseDto usuarioBaseDto = new UsuarioBaseDto();
        usuarioBaseDto.setNombres("Cristian David");
        usuarioBaseDto.setApellidos("Zapata Lazaro");

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setNombres("Cristian David");
        usuarioDto.setApellidos("Zapata Lazaro");

        when(usuarioService.crearUsuario(any(UsuarioBaseDto.class))).thenReturn(usuarioDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioBaseDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombres").value("Cristian David"))
                .andExpect(jsonPath("$.apellidos").value("Zapata Lazaro"));
    }

    @Test
    void crearUsuarioFalla() throws Exception {

        UsuarioBaseDto usuarioBaseDto = new UsuarioBaseDto();

        when(usuarioService.crearUsuario(any(UsuarioBaseDto.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioBaseDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Parametros invalidos"));
    }

    @Test
    void actualizarUsuarioExito() throws Exception {

        UsuarioBaseDto usuarioBaseDto = new UsuarioBaseDto();
        usuarioBaseDto.setNombres("Cristian David 2");
        usuarioBaseDto.setApellidos("Zapata Lazaro 2");

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setNombres("Cristian David 2");
        usuarioDto.setApellidos("Zapata Lazaro 2");

        when(usuarioService.obtenerUsuario(1L)).thenReturn(usuarioDto);
        when(usuarioService.actualizarUsuario(eq(1L), any(UsuarioBaseDto.class), any(UsuarioDto.class)))
                .thenReturn(usuarioDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioBaseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres").value("Cristian David 2"))
                .andExpect(jsonPath("$.apellidos").value("Zapata Lazaro 2"));
    }

    @Test
    void actualizarUsuarioNoExiste() throws Exception {

        UsuarioBaseDto usuarioBaseDto = new UsuarioBaseDto();
        usuarioBaseDto.setNombres("Cristian David");
        usuarioBaseDto.setApellidos("Zapata Lazaro");

        when(usuarioService.obtenerUsuario(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioBaseDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    void obtenerUsuarioExito() throws Exception {

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setNombres("Cristian David");
        usuarioDto.setApellidos("Zapata Lazaro");

        when(usuarioService.obtenerUsuario(1L)).thenReturn(usuarioDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombres").value("Cristian David"))
                .andExpect(jsonPath("$.apellidos").value("Zapata Lazaro"));
    }

    @Test
    void obtenerUsuarioNoExiste() throws Exception {

        when(usuarioService.obtenerUsuario(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    void obtenerUsuariosExistenU() throws Exception {

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setNombres("Cristian David");
        usuarioDto.setApellidos("Zapata Lazaro");

        when(usuarioService.obtenerUsuarios()).thenReturn(List.of(usuarioDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombres").value("Cristian David"))
                .andExpect(jsonPath("$[0].apellidos").value("Zapata Lazaro"));
    }

    @Test
    void obtenerUsuariosNoExisten() throws Exception {

        when(usuarioService.obtenerUsuarios()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

}
