package com.imd.web2.controllers;

import com.imd.web2.entities.ClienteEntity;
import com.imd.web2.repositories.ClienteRepository;
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
    ClienteRepository clienteRepository;

    @GetMapping("getAll")
    public ResponseEntity<Object> getAllClientes() {
        return ResponseEntity.ok().body(clienteRepository.findAll());
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Optional<ClienteEntity> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }
        return ResponseEntity.ok().body(cliente.get());
    }
}
