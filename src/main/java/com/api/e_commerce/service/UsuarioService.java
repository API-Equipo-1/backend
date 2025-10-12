package com.api.e_commerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import com.api.e_commerce.exception.UsuarioNotFoundException;
import com.api.e_commerce.model.Usuario;
import com.api.e_commerce.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    //getAllUsuarios 
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    //saveUsuario
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long usuario_id) {
        usuarioRepository.deleteById(usuario_id);
    }

    public Usuario getUsuarioById(Long usuario_id) {
        return usuarioRepository.findById(usuario_id).orElseThrow(() -> new UsuarioNotFoundException(usuario_id));
    }

}
