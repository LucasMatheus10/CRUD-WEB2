package com.imd.web2.entities;

import com.imd.web2.entities.DTO.ClienteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
public class ClienteEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String nome;
    String cpf;
    public enum Genero {
        MASCULINO,
        FEMININO
    }
    Genero genero;
    String dataNascimento;
    Boolean ativo = true;


    public ClienteEntity(ClienteDTO clienteDTO) {
        this.nome = clienteDTO.nome();
        this.cpf = clienteDTO.cpf();
        this.genero = clienteDTO.genero();
        this.dataNascimento = clienteDTO.dataNascimento();
    }

    public void carregarDTO(ClienteDTO cliente){
        if(cliente.nome() != null){
            this.nome = cliente.nome();
        }
        if(cliente.cpf() != null){
            this.cpf = cliente.cpf();
        }
        if(cliente.genero() != null){
            this.genero = cliente.genero();
        }
        if(cliente.dataNascimento() != null){
            this.dataNascimento = cliente.dataNascimento();
        }
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

}
