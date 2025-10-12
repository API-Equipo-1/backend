package com.api.e_commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.e_commerce.model.Producto;
import com.api.e_commerce.service.ProductoService;
import com.api.e_commerce.dto.ProductoUpdateDTO;

@RestController
@RequestMapping("/api/productos") //localhost:8080/api/productos del locahost:8080/api/productos/id
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    //https://localhost:8080/api/productos con metodo get http
    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    // https://localhost:8080/api/productos/3 con metodo get http
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Producto producto= productoService.getProductoById(id);

        
        return new ResponseEntity<Producto>(producto, HttpStatus.OK);
    }

    //https://localhost:8080/api/productos con metodo POST http, enviar un body
    @PostMapping
    //TODO: ssanchez - cambiar Producto por ProductoCreateDTO, es mala práctica recibir la entidad, debe recibir un DTO
    // ProductoCreateDTO debe tener los campos obligatorios para crear un producto
    public Producto addProducto(@RequestBody Producto producto) {
        return productoService.saveProducto(producto);
    }    //https://localhost:8080/api/productos/1 con metodo put http, enviar un body
    
    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody ProductoUpdateDTO productoDTO) {
        return productoService.updateProducto(id, productoDTO);
    }

    //https://localhost:8080/api/productos/1 con metodo delete http
    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
    }
}
