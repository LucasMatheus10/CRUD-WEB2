package com.imd.web2.entities.DTO;

import com.imd.web2.entities.ProdutoEntity;

public record ProdutoDTO(
        String nomeProduto,
        String marca,
        String dataFabricacao,
        String dataValidade,
        ProdutoEntity.Genero genero,
        String lote
) {
}