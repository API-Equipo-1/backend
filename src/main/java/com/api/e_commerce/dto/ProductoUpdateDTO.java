package com.api.e_commerce.dto;

import com.api.e_commerce.model.Categoria;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ProductoUpdateDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String imagen;
    private List<Categoria> categorias;
}
