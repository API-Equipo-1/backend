package com.api.e_commerce.controller;

import com.api.e_commerce.model.Direccion;
import com.api.e_commerce.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {
    @Autowired
    private DireccionService direccionService;

    @GetMapping
    public List<Direccion> getAllDireccion() {
        return direccionService.getAllDireccion();
    }

    @GetMapping("/{id}")
    public Direccion getDireccionById(Long id) {
        return direccionService.getDireccionById(id);
    }
    @PostMapping
    public Direccion save(Direccion direccion) {
        return direccionService.save(direccion);
    }
    @DeleteMapping
    public void deleteDireccion(Long id) {
        direccionService.deleteDireccion(id);
    }

}
