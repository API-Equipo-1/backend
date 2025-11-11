package com.api.e_commerce.mapper;

import com.api.e_commerce.dto.ProductoDTO;
import com.api.e_commerce.dto.ProductoUpdateDTO;
import com.api.e_commerce.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    @Mapping(source = "usuarioId", target = "usuario.id")
    Producto toEntity(ProductoDTO dto);
    
    @Mapping(source = "usuario.id", target = "usuarioId")
    ProductoDTO toDTO(Producto entity);
    
    List<ProductoDTO> toDTOList(List<Producto> entities);
    ProductoUpdateDTO toDto(Producto entity);
}