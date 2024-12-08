package com.imd.web2.entities;

import com.imd.web2.entities.DTO.ClienteDTO;
import com.imd.web2.entities.DTO.PedidoDTO;
import com.imd.web2.repositories.PedidoRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pedidos")
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String codigo;
    @ManyToMany
    @JoinTable(
        name = "pedido_produto",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    List<ProdutoEntity> produtos;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    ClienteEntity cliente;
    Boolean ativo = true;


    public PedidoEntity(PedidoDTO pedidoDTO){
        this.codigo = pedidoDTO.codigo();
    }

    public void carregarDTO(PedidoDTO pedido){
        if(pedido.codigo() != null){
            this.codigo = pedido.codigo();
        }
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
