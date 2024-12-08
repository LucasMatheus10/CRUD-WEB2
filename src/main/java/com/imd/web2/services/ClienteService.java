package com.imd.web2.services;

import com.imd.web2.entities.ClienteEntity;
import com.imd.web2.entities.DTO.ClienteDTO;
import com.imd.web2.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        Optional<ClienteEntity> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            return null;
        }
        cliente.get().carregarDTO(clienteDTO);
        if (!cliente.get().getAtivo()) {
            return null;
        }
        clienteRepository.save(cliente.get());
        return cliente.get();
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
