package com.imd.web2.entities.DTO;

import com.imd.web2.entities.ProdutoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoDTO(
        @NotNull
        @NotBlank
        String nomeProduto,
        @NotNull
        String marca,
        @NotNull
        String dataFabricacao,
        @NotNull
        @NotBlank
        String dataValidade,
        ProdutoEntity.Genero genero,
        @NotNull
        String lote
) {
}