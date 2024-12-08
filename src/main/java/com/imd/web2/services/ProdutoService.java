package com.imd.web2.services;

import com.imd.web2.entities.DTO.ProdutoDTO;
import com.imd.web2.entities.ProdutoEntity;
import com.imd.web2.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
        Optional<ProdutoEntity> produto = produtoRepository.findById(id);
        if (produto.isEmpty()) {
            return null;
        }
        produto.get().carregarDTO(produtoDTO);
        if (!produto.get().getAtivo()) {
            return null;
        }
        produtoRepository.save(produto.get());
        return produto.get();
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
