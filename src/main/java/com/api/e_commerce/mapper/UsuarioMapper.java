package com.api.e_commerce.mapper;

import org.mapstruct.Mapper;

import com.api.e_commerce.dto.UsuarioDTO;
import com.api.e_commerce.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDTO toDTO(Usuario usuario);    
}
