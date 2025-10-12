package com.api.e_commerce.service;

import com.api.e_commerce.model.Categoria;
import com.api.e_commerce.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public void deleteCategoria(Long categoria_id) {
        categoriaRepository.deleteById(categoria_id);
    }
    public List<Categoria> getAllCategoria() {
        return categoriaRepository.findAll();

    }

    public Categoria getCategoriaById(Long categoria_id) {
        return categoriaRepository.findById(categoria_id).orElse(null);
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }




}
