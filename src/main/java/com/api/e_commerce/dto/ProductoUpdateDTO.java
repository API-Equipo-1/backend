package com.api.e_commerce.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductoUpdateDTO {
    private String nombre;
    private Double precio;
    private Integer stock;
}
