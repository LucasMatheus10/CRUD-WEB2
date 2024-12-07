package com.imd.web2.controllers;

import com.imd.web2.entities.DTO.ClienteDTO;
import com.imd.web2.entities.ClienteEntity;
import com.imd.web2.repositories.ClienteRepository;
import com.imd.web2.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @GetMapping
    public String olaCliente() {
        return "Ola Cliente";
    }

    @Autowired
    ClienteService clienteService;

    @GetMapping("getAll")
    public ResponseEntity<Object> getAllClientes() {
        return ResponseEntity.ok().body(clienteService.getAllClientes());
    }

    @GetMapping("getAllByAtivo")
    public ResponseEntity<Object> getAllByAtivo() {
        return ResponseEntity.ok().body(clienteService.getAllByAtivo());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        var cliente = clienteService.getById(id);
        if(cliente == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }
        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping()
    public ResponseEntity<Object> postClient(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.postClient(clienteDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putClient(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        var cliente = clienteService.putClient(id, clienteDTO);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
        return ResponseEntity.ok().body(cliente);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable Long id) {
        var cliente = clienteService.deleteClient(id);
        if (cliente) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }
    }

    @PutMapping("deleteLogic/{id}")
    public ResponseEntity<Object> deleteLogic(@PathVariable Long id) {
        var cliente = clienteService.deleteLogic(id);
        if (cliente) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }
    }
}
