package com.imd.web2.controllers;

import com.imd.web2.DTO.ClienteDTO;
import com.imd.web2.DTO.ProdutoDTO;
import com.imd.web2.entities.ClienteEntity;
import com.imd.web2.repositories.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Optional<ClienteEntity> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
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
        Optional<ClienteEntity> updated = clienteRepository.findById(id);
        BeanUtils.copyProperties(clienteDTO, updated, getNullPropertyNames(clienteDTO, "id"));
        return ResponseEntity.ok().body(clienteRepository.save(updated.get()));
    }

    public static String[] getNullPropertyNames(Object source, String... ignoreProperties) {
        // cria um beanWrapper para o objeto de origem para acessar suas propriedades
        final BeanWrapper sourceWrapper = new BeanWrapperImpl(source);

        // pega todos os descritores de propriedade do objeto
        PropertyDescriptor[] propertyDescriptors = sourceWrapper.getPropertyDescriptors();

        Set<String> nullPropertyNames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            // pega o valor da propriedade correspondente
            Object propertyValue = sourceWrapper.getPropertyValue(propertyDescriptor.getName());
            if (propertyValue == null) {
                nullPropertyNames.add(propertyDescriptor.getName());
            }
        }

        // Ignora as propriedades especificadas na lista de ignoreProperties
        for (String ignoreProperty : ignoreProperties) {
            nullPropertyNames.add(ignoreProperty);
        }

        // converte o conjunto de nomes de propriedades null em um array e o retorna
        return nullPropertyNames.toArray(new String[0]);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }
    }

//    @DeleteMapping("deleteLogic")

}
