package com.api.e_commerce.dto;

import com.api.e_commerce.model.Categoria;
import lombok.Data;

import java.util.List;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private List<Categoria> categorias;

}
