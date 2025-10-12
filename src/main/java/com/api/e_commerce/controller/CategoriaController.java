package com.api.e_commerce.controller;

import com.api.e_commerce.model.Categoria;
import com.api.e_commerce.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> getCategorias(){
        return categoriaService.getAllCategoria();
    }

    @GetMapping
    public Categoria getCategoriaById(@RequestParam Long id){
        return categoriaService.getCategoriaById(id);
    }

    @DeleteMapping
    public void deleteCategoria(@RequestParam Long id){
        categoriaService.deleteCategoria(id);
    }

    @PostMapping
    public Categoria save(@RequestBody Categoria categoria){
        return categoriaService.save(categoria);
    }
}
