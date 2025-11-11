package com.api.e_commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "categorias")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)    
    private String nombre;

    @JsonIgnore
    @ManyToMany(mappedBy = "categorias")
    private List<Producto> productos = new ArrayList<>();
}
