package com.api.e_commerce.service;

import java.util.List;

import com.api.e_commerce.dto.ProductoDTO;
import com.api.e_commerce.mapper.ProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.e_commerce.model.Producto;
import com.api.e_commerce.repository.ProductoRepository;
import com.api.e_commerce.dto.ProductoUpdateDTO;

@Service
@Transactional
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    public ProductoService(ProductoMapper productoMapper) {
        this.productoMapper = productoMapper;
    }

    public List<ProductoDTO> getAllProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productoMapper.toDTOList(productos);
    }

    public ProductoDTO getProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoMapper.toDTO(producto);
    }

    public ProductoDTO addProducto(ProductoDTO productoDTO){
        Producto producto = productoMapper.toEntity(productoDTO);
        Producto savedProduct = productoRepository.save(producto);
        return productoMapper.toDTO(savedProduct);
    }

    public void deleteProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        productoRepository.delete(producto);
    }

    public ProductoUpdateDTO updateProducto(Long id, ProductoUpdateDTO updateDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        if (updateDTO.getNombre() != null) producto.setNombre(updateDTO.getNombre());
        if (updateDTO.getPrecio() != null) producto.setPrecio(updateDTO.getPrecio());
        if (updateDTO.getStock() != null) producto.setStock(updateDTO.getStock());

        Producto savedProduct = productoRepository.save(producto);
        return productoMapper.toDto(savedProduct);
    }
}
