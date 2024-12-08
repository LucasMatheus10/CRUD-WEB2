package com.imd.web2.controllers;

import com.imd.web2.entities.DTO.PedidoDTO;
import com.imd.web2.entities.DTO.ProdutoDTO;
import com.imd.web2.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping("getAll")
    public ResponseEntity<Object> getAllPedidos() {
        return ResponseEntity.ok().body(pedidoService.getAll());
    }

    @GetMapping("getAllByAtivo")
    public ResponseEntity<Object> getAllByAtivo() {
        return ResponseEntity.ok().body(pedidoService.getAllByAtivo());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        var pedido = pedidoService.getById(id);
        if(pedido == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não localizado");
        }
        return ResponseEntity.ok().body(pedido);
    }

    @PostMapping()
    public ResponseEntity<Object> postPedido(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.postPedido(pedidoDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        var pedido = pedidoService.putPedido(id, pedidoDTO);
        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado");
        }
        return ResponseEntity.ok().body(pedido);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletePedido(@PathVariable Long id) {
        var pedido = pedidoService.deletePedido(id);
        if (pedido) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não localizado");
        }
    }

    @PutMapping("deleteLogic/{id}")
    public ResponseEntity<Object> deleteLogic(@PathVariable Long id) {
        var pedido = pedidoService.deleteLogic(id);
        if (pedido) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não localizado");
        }
    }

    @PostMapping("addProduct/{id}")
    public ResponseEntity<Object> addProduct(@PathVariable Long id, @RequestBody ProdutoDTO produto) {
        return ResponseEntity.ok().body(pedidoService.addProduct(id, produto));
    }

    @DeleteMapping("removeProduct/{id}")
    public ResponseEntity<Object> removeProduct(@PathVariable Long id, @RequestBody ProdutoDTO produto) {
        return ResponseEntity.ok().body(pedidoService.removeProduct(id, produto));
    }

}