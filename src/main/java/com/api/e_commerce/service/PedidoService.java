package com.api.e_commerce.service;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.e_commerce.exception.PedidoNotFoundException;
import com.api.e_commerce.model.Pedido;

import com.api.e_commerce.repository.PedidoRepository;

@Service
@Transactional
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> getAllPedidos() {
       
        return pedidoRepository.findAll();
    }

    public Pedido createPedido(Pedido pedido) {
        
        return pedidoRepository.save(pedido);
    }

    public Pedido getPedidoById(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
    }  

    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }
    
}
