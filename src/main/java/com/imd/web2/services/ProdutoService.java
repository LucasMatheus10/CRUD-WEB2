package com.imd.web2.services;

import com.imd.web2.entities.DTO.ProdutoDTO;
import com.imd.web2.entities.ProdutoEntity;
import com.imd.web2.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public List<ProdutoEntity> getAllprodutos() {
        return produtoRepository.findAll();
    }

    public List<ProdutoEntity> getAllByAtivo() {
        return produtoRepository.findAllByAtivoTrue();
    }

    public ProdutoEntity getById(Long id) {
        Optional<ProdutoEntity> produto = produtoRepository.findById(id);
        if(produto.isEmpty()){
            return null;
        }
        if(!produto.get().getAtivo()){
            return null;
        }
        return produto.get();
    }

    public ProdutoEntity postProduto(ProdutoDTO produtoDTO) {
        ProdutoEntity produto = new ProdutoEntity(produtoDTO);
        return produtoRepository.save(produto);
    }

    public ProdutoEntity putProduto(Long id, ProdutoDTO produtoDTO) {
        ProdutoEntity produtoAtualizado = new ProdutoEntity(produtoDTO);
        Optional<ProdutoEntity> produtoBase = produtoRepository.findById(id);

        if (produtoBase.isEmpty()) {
            return null;
        }

        if (!produtoBase.get().getAtivo()) {
            return null;
        }

        if (produtoAtualizado.getLote() != null && !produtoAtualizado.getLote().equals(produtoBase.get().getLote())) {
            produtoBase.get().setLote(produtoAtualizado.getLote());
        }
        if (produtoAtualizado.getNomeProduto() != null && !produtoAtualizado.getNomeProduto().equals(produtoBase.get().getNomeProduto())) {
            produtoBase.get().setNomeProduto(produtoAtualizado.getNomeProduto());
        }
        if (produtoAtualizado.getGenero() != null && produtoAtualizado.getGenero() != produtoBase.get().getGenero()) {
            produtoBase.get().setGenero(produtoAtualizado.getGenero());
        }
        if (produtoAtualizado.getDataFabricacao() != null && !produtoAtualizado.getDataFabricacao().equals(produtoBase.get().getDataFabricacao())) {
            produtoBase.get().setDataFabricacao(produtoAtualizado.getDataFabricacao());
        }
        if (produtoAtualizado.getDataValidade() != null && !produtoAtualizado.getDataValidade().equals(produtoBase.get().getDataValidade())) {
            produtoBase.get().setDataValidade(produtoAtualizado.getDataValidade());
        }
        if (produtoAtualizado.getMarca() != null && !produtoAtualizado.getMarca().equals(produtoBase.get().getMarca())) {
            produtoBase.get().setMarca(produtoAtualizado.getMarca());
        }

        produtoRepository.save(produtoBase.get());
        return produtoBase.get();
    }

    public boolean deleteProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteLogic(Long id) {
        Optional<ProdutoEntity> produto;
        if (produtoRepository.existsById(id)) {
            produto = produtoRepository.findById(id);
            produto.get().desativar();
            produtoRepository.save(produto.get());
            return true;
        } else {
            return false;
        }
    }
}
