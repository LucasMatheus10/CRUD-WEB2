package com.imd.web2.controllers;

import com.imd.web2.entities.DTO.ProdutoDTO;
import com.imd.web2.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping("getAll")
    public ResponseEntity<Object> getAllprodutos() {
        return ResponseEntity.ok().body(produtoService.getAllprodutos());
    }

    @GetMapping("getAllByAtivo")
    public ResponseEntity<Object> getAllByAtivo() {
        return ResponseEntity.ok().body(produtoService.getAllByAtivo());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        var produto = produtoService.getById(id);
        if(produto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
        return ResponseEntity.ok().body(produto);
    }

    @PostMapping()
    public ResponseEntity<Object> postProduto(@RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.postProduto(produtoDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        var produto = produtoService.putProduto(id, produtoDTO);
        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        return ResponseEntity.ok().body(produto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteProduto(@PathVariable Long id) {
        var produto = produtoService.deleteProduto(id);
        if (produto) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
    }

    @PutMapping("deleteLogic/{id}")
    public ResponseEntity<Object> deleteLogic(@PathVariable Long id) {
        var produto = produtoService.deleteLogic(id);
        if (produto) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
    }

}
