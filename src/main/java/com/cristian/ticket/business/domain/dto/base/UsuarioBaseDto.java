package com.cristian.ticket.business.domain.dto.base;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Datos del usuario")
@Data
public class UsuarioBaseDto implements Serializable {

    @Schema(description = "Nombres del usuario")
    @NotBlank(message = "Parametros invalidos")
    @NotNull(message = "Parametros invalidos")
    private String nombres;

    @Schema(description = "Apellidos del usuario")
    @NotBlank(message = "Parametros invalidos")
    @NotNull(message = "Parametros invalidos")
    private String apellidos;

}
