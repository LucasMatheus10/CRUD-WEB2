package com.imd.web2.entities.DTO;

import com.imd.web2.entities.ClienteEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PedidoDTO(
        @NotNull
        @NotBlank(message = "Não pode estar vazio!")
        String codigo,
        ClienteEntity cliente
) {
}
