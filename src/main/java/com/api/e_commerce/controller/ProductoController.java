package com.api.e_commerce.controller;

import java.util.List;

import com.api.e_commerce.dto.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.e_commerce.service.ProductoService;
import com.api.e_commerce.dto.ProductoDTO;
import com.api.e_commerce.dto.ProductoUpdateDTO;

@RestController
@RequestMapping("/api/productos") //localhost:8080/api/productos del locahost:8080/api/productos/id
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    //https://localhost:8080/api/productos con metodo get http
    @GetMapping
    public List<ProductoDTO> getAllProductos() {
        return productoService.getAllProductos();
    }

    // https://localhost:8080/api/productos/3 con metodo get http
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        ProductoDTO producto = productoService.getProductoById(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    //https://localhost:8080/api/productos con metodo POST http, enviar un body
    @PostMapping
    public ProductoDTO addProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.addProducto(productoDTO);
    }

    //https://localhost:8080/api/productos/1 con metodo put http, enviar un body
    
    @PutMapping("/{id}")
    public ProductoUpdateDTO updateProducto(@PathVariable Long id, @RequestBody ProductoUpdateDTO productoUpdateDTO) {
        return productoService.updateProducto(id, productoUpdateDTO);
    }

    //https://localhost:8080/api/productos/1 con metodo delete http
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}
