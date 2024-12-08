package com.imd.web2.entities;

import com.imd.web2.entities.DTO.ProdutoDTO;
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
    Boolean ativo = true;

    public ProdutoEntity(ProdutoDTO produtoDTO) {
        this.nomeProduto = produtoDTO.nomeProduto();
        this.marca = produtoDTO.marca();
        this.dataFabricacao = produtoDTO.dataFabricacao();
        this.dataValidade = produtoDTO.dataValidade();
        this.genero = produtoDTO.genero();
        this.lote = produtoDTO.lote();
    }

    public void carregarDTO(ProdutoDTO produto){
        if(produto.nomeProduto() != null){
            this.nomeProduto = produto.nomeProduto();
        }
        if(produto.marca() != null){
            this.marca = produto.marca();
        }
        if(produto.dataFabricacao() != null){
            this.dataFabricacao = produto.dataFabricacao();
        }
        if(produto.dataValidade() != null){
            this.dataValidade = produto.dataValidade();
        }
        if(produto.genero() != null){
            this.genero = produto.genero();
        }
        if(produto.lote() != null){
            this.lote = produto.lote();
        }
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}