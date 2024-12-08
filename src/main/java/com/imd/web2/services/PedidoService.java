package com.imd.web2.services;

import com.imd.web2.entities.ClienteEntity;
import com.imd.web2.entities.DTO.PedidoDTO;
import com.imd.web2.entities.DTO.ProdutoDTO;
import com.imd.web2.entities.PedidoEntity;
import com.imd.web2.entities.ProdutoEntity;
import com.imd.web2.repositories.PedidoRepository;
import com.imd.web2.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<PedidoEntity> getAll() {
        return pedidoRepository.findAll();
    }

    public List<PedidoEntity> getAllByAtivo() {
        return pedidoRepository.findAllByAtivoTrue();
    }

    public PedidoEntity getById(Long id) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty()) {
            return null;
        }
        return pedido.get();
    }

    public PedidoEntity postPedido(PedidoDTO pedidoDTO) {
        PedidoEntity pedido = new PedidoEntity(pedidoDTO);
        return pedidoRepository.save(pedido);
    }

    public PedidoEntity putPedido(Long id, PedidoDTO pedidoDTO) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty()) {
            return null;
        }
        pedido.get().carregarDTO(pedidoDTO);
        pedidoRepository.save(pedido.get());
        return pedido.get();
    }

    public boolean deletePedido(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteLogic(Long id) {
        if (pedidoRepository.existsById(id)) {
            Optional<PedidoEntity> pedido = pedidoRepository.findById(id);
            pedido.get().desativar();
            pedidoRepository.save(pedido.get());
            return true;
        } else {
            return false;
        }
    }

    public List<ProdutoEntity> addProduct (Long id, ProdutoDTO produtoDTO) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(id);
        ProdutoEntity produto = new ProdutoEntity(produtoDTO);
        if (pedido.isEmpty()) {
            return null;
        }
        pedido.get().getProdutos().add(produto);
        pedidoRepository.save(pedido.get());
        return pedido.get().getProdutos();
    }

    public List<ProdutoEntity> removeProduct (Long id, ProdutoDTO produtoDTO) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(id);
        ProdutoEntity produto = new ProdutoEntity(produtoDTO);
        if (pedido.isEmpty()) {
            return null;
        }
        pedido.get().getProdutos().remove(produto);
        pedidoRepository.save(pedido.get());
        return pedido.get().getProdutos();
    }
}
