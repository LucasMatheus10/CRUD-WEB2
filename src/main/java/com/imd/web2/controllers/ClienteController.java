package com.imd.web2.controllers;

import com.imd.web2.DTO.ClienteDTO;
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

    @GetMapping("getAllByAtivo")
    public ResponseEntity<Object> getAllByAtivo() {
        return ResponseEntity.ok().body(clienteRepository.findAllByAtivoTrue());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Optional<ClienteEntity> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }
        if(!cliente.get().getAtivo()){
            return ResponseEntity.status(HttpStatus.LOCKED).body("Cliente não está ativo");
        }
        return ResponseEntity.ok().body(cliente.get());
    }

    @PostMapping()
    public ResponseEntity<Object> postClient(@RequestBody ClienteDTO clienteDTO) {
        ClienteEntity cliente = new ClienteEntity(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(cliente));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putClient(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        ClienteEntity clienteAtualizado = new ClienteEntity(clienteDTO);
        Optional<ClienteEntity> clienteBase = clienteRepository.findById(id);

        if (clienteBase.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

        if (!clienteBase.get().getAtivo()) {
            return ResponseEntity.status(HttpStatus.LOCKED).body("Cliente não está ativo");
        }

        if (clienteAtualizado.getCpf() != null && !clienteAtualizado.getCpf().equals(clienteBase.get().getCpf())) {
            clienteBase.get().setCpf(clienteAtualizado.getCpf());
        }
        if (clienteAtualizado.getNome() != null && !clienteAtualizado.getNome().equals(clienteBase.get().getNome())) {
            clienteBase.get().setNome(clienteAtualizado.getNome());
        }
        if (clienteAtualizado.getGenero() != null && clienteAtualizado.getGenero() != clienteBase.get().getGenero()) {
            clienteBase.get().setGenero(clienteAtualizado.getGenero());
        }
        if (clienteAtualizado.getDataNascimento() != null && !clienteAtualizado.getDataNascimento().equals(clienteBase.get().getDataNascimento())) {
            clienteBase.get().setDataNascimento(clienteAtualizado.getDataNascimento());
        }
        clienteRepository.save(clienteBase.get());
        return ResponseEntity.ok().body(clienteBase.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }
    }

    @PutMapping("deleteLogic/{id}")
    public ResponseEntity<Object> deleteLogic(@PathVariable Long id) {
        Optional<ClienteEntity> cliente;
        if (clienteRepository.existsById(id)) {
            cliente = clienteRepository.findById(id);
            cliente.get().desativar();
            clienteRepository.save(cliente.get());
            return ResponseEntity.ok().body(cliente.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }
    }
}
