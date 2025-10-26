package com.api.e_commerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.e_commerce.model.Producto;
import com.api.e_commerce.repository.ProductoRepository;
import com.api.e_commerce.dto.ProductoDTO;
import com.api.e_commerce.dto.ProductoUpdateDTO;
import com.api.e_commerce.exception.ProductoNotFoundException;

@Service
@Transactional
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(Long id) {
        // return productoRepository.findById(id).orElse(null);

        if (id == 0) {
            throw new IllegalArgumentException("El id es del admin");
        }

        return this.productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

    }

    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto createProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setCategorias(productoDTO.getCategorias());
        
        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }    

    public Producto updateProducto(Long id, ProductoUpdateDTO productoDTO) {
        

        return productoRepository.findById(id)
            .map(producto -> {
                producto.setPrecio(productoDTO.getPrecio());
                producto.setStock(productoDTO.getStock());
                return productoRepository.save(producto);
            })
            .orElse(null);
    }

    

}
