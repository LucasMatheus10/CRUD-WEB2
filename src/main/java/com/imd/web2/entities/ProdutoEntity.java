package com.imd.web2.entities;

import com.imd.web2.DTO.ProdutoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
public class ProdutoEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String nomeProduto;
    String marca;
    String dataFabricacao;
    String dataValidade;

    public enum Genero {
        COSMETICO,
        ALIMENTICIO,
        HIGIENE,
        PESSOAL,
        LIMPEZA,
    }

    Genero genero;
    String lote;

    public ProdutoEntity(ProdutoDTO produtoDTO) {
        this.nomeProduto = produtoDTO.nomeProduto();
        this.marca = produtoDTO.marca();
        this.dataFabricacao = produtoDTO.dataFabricacao();
        this.dataValidade = produtoDTO.dataValidade();
        this.genero = produtoDTO.genero();
        this.lote = produtoDTO.lote();
    }
}