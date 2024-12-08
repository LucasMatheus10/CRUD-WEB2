package com.imd.web2.entities.DTO;

import com.imd.web2.entities.ClienteEntity;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteDTO(
        @NotNull
        String nome,
        @NotNull(message = "O CPF não pode ser vazio")
        @CPF(message = "Digite um valor válido para CPF")
        String cpf,
        ClienteEntity.Genero genero,
        @NotNull
        String dataNascimento
) {
}
