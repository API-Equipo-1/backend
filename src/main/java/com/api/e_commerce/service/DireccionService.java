package com.api.e_commerce.service;

import com.api.e_commerce.model.Direccion;
import com.api.e_commerce.repository.DireccionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class DireccionService {
    @Autowired
    private DireccionRepository direccionRepository;

    public Direccion save(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    public void deleteDireccion(Long direccion_id) {
        direccionRepository.deleteById(direccion_id);
    }

    public Direccion getDireccionById(Long direccion_id) {
        return direccionRepository.findById(direccion_id).orElse(null);
    }

    public List<Direccion> getAllDireccion() {
        return direccionRepository.findAll();
    }



}
