package com.imd.web2.entities.DTO;

import com.imd.web2.entities.ClienteEntity;
import com.imd.web2.entities.ProdutoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PedidoDTO(
        @NotNull
        @NotBlank(message = "Não pode estar vazio!")
        String codigo
) {
}

