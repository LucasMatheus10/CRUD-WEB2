package com.imd.web2.controllers;

import com.imd.web2.DTO.ProdutoDTO;
import com.imd.web2.entities.ClienteEntity;
import com.imd.web2.entities.ProdutoEntity;
import com.imd.web2.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @GetMapping
    public String olaProduto() {
        return "Ola produto";
    }

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("getAll")
    public ResponseEntity<Object> getAllprodutos() {
        return ResponseEntity.ok().body(produtoRepository.findAll());
    }

    @GetMapping("getAllByAtivo")
    public ResponseEntity<Object> getAllByAtivo() {
        return ResponseEntity.ok().body(produtoRepository.findAllByAtivoTrue());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Optional<ProdutoEntity> produto = produtoRepository.findById(id);
        if(produto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
        if(!produto.get().getAtivo()){
            return ResponseEntity.status(HttpStatus.LOCKED).body("Produto não está ativo");
        }
        return ResponseEntity.ok().body(produto.get());
    }

    @PostMapping()
    public ResponseEntity<Object> postClient(@RequestBody ProdutoDTO produtoDTO) {
        ProdutoEntity produto = new ProdutoEntity(produtoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putClient(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        ProdutoEntity produtoAtualizado = new ProdutoEntity(produtoDTO);
        Optional<ProdutoEntity> produtoBase = produtoRepository.findById(id);

        if (produtoBase.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        if (!produtoBase.get().getAtivo()) {
            return ResponseEntity.status(HttpStatus.LOCKED).body("Produto não está ativo");
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
        return ResponseEntity.ok().body(produtoBase.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
    }

    @PutMapping("deleteLogic/{id}")
    public ResponseEntity<Object> deleteLogic(@PathVariable Long id) {
        Optional<ProdutoEntity> produto;
        if (produtoRepository.existsById(id)) {
            produto = produtoRepository.findById(id);
            produto.get().desativar();
            produtoRepository.save(produto.get());
            return ResponseEntity.ok().body(produto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
    }

}
