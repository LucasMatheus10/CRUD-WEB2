package com.imd.web2.DTO;

import com.imd.web2.entities.ClienteEntity;

public record ClienteDTO(
        String nome,
        String cpf,
        ClienteEntity.Genero genero,
        String dataNascimento
) {
}
