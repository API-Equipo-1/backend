package com.api.e_commerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.api.e_commerce.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}