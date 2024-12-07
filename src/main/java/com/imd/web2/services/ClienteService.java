package com.imd.web2.services;

import com.imd.web2.entities.ClienteEntity;
import com.imd.web2.entities.DTO.ClienteDTO;
import com.imd.web2.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public List<ClienteEntity> getAllClientes() {
        return clienteRepository.findAll();
    }

    public List<ClienteEntity> getAllByAtivo() {
        return clienteRepository.findAllByAtivoTrue();
    }

    public ClienteEntity getById(Long id) {
        Optional<ClienteEntity> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()){
            return null;
        }
        if(!cliente.get().getAtivo()){
            return null;
        }
        return cliente.get();
    }

    public ClienteEntity postClient(ClienteDTO clienteDTO) {
        ClienteEntity cliente = new ClienteEntity(clienteDTO);
        return clienteRepository.save(cliente);
    }

    public ClienteEntity putClient(Long id, ClienteDTO clienteDTO) {
        ClienteEntity clienteAtualizado = new ClienteEntity(clienteDTO);
        Optional<ClienteEntity> clienteBase = clienteRepository.findById(id);

        if (clienteBase.isEmpty()) {
            return null;
        }

        if (!clienteBase.get().getAtivo()) {
            return null;
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
        return clienteBase.get();
    }

    public boolean deleteClient(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteLogic(Long id) {
        Optional<ClienteEntity> cliente;
        if (clienteRepository.existsById(id)) {
            cliente = clienteRepository.findById(id);
            cliente.get().desativar();
            clienteRepository.save(cliente.get());
            return true;
        } else {
            return false;
        }
    }
}
