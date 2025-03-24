package com.cristian.ticket.business.domain.dto.base;

import java.io.Serializable;

import com.cristian.ticket.business.domain.util.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Datos del ticket")
@Data
public class TicketBaseDto implements Serializable {

    @Schema(description = "Descripción del ticket")
    @NotBlank(message = "Parametros invalidos")
    @NotNull(message = "Parametros invalidos")
    @Size(max = 500, message = "Parametros invalidos")
    private String descripcion;

    @Schema(description = "Identificador del usuario del ticket")
    @NotNull(message = "Parametros invalidos")
    private Long usuarioId;

    @Schema(description = "Status del ticket")
    @NotNull(message = "Parametros invalidos")
    private Status status;

}
