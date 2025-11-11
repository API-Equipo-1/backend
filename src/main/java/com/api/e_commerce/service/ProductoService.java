package com.api.e_commerce.service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.api.e_commerce.dto.ProductoDTO;
import com.api.e_commerce.mapper.ProductoMapper;
import com.api.e_commerce.model.Categoria;
import com.api.e_commerce.model.Usuario;
import com.api.e_commerce.repository.CategoriaRepository;
import com.api.e_commerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.e_commerce.model.Producto;
import com.api.e_commerce.repository.ProductoRepository;
import com.api.e_commerce.dto.ProductoUpdateDTO;

@Service
@Transactional
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final ProductoMapper productoMapper;

    public ProductoService(ProductoMapper productoMapper) {
        this.productoMapper = productoMapper;
    }

    public List<ProductoDTO> getAllProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productoMapper.toDTOList(productos);
    }
    
    public List<ProductoDTO> getProductosByUsuarioId(Long usuarioId) {
        List<Producto> productos = productoRepository.findByUsuarioId(usuarioId);
        return productoMapper.toDTOList(productos);
    }

    public ProductoDTO getProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoMapper.toDTO(producto);
    }

    public ProductoDTO addProducto(ProductoDTO productoDTO){
        Producto producto = productoMapper.toEntity(productoDTO);
        
        // Si viene usuarioId, buscar y asignar el usuario
        if (productoDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(productoDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + productoDTO.getUsuarioId()));
            producto.setUsuario(usuario);
        }
        
        // Manejar categorías al crear producto
        if (productoDTO.getCategorias() != null && !productoDTO.getCategorias().isEmpty()) {
            List<Categoria> categoriasGestionadas = productoDTO.getCategorias().stream()
                .map(categoria -> {
                    // Si la categoría tiene ID, buscarla
                    if (categoria.getId() != null) {
                        return categoriaRepository.findById(categoria.getId())
                            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + categoria.getId()));
                    }
                    // Si no tiene ID, buscar por nombre o crear nueva
                    else if (categoria.getNombre() != null) {
                        return categoriaRepository.findByNombre(categoria.getNombre())
                            .orElseGet(() -> {
                                Categoria nuevaCategoria = new Categoria();
                                nuevaCategoria.setNombre(categoria.getNombre());
                                return categoriaRepository.save(nuevaCategoria);
                            });
                    }
                    throw new RuntimeException("Categoría inválida: debe tener ID o nombre");
                })
                .collect(Collectors.toList());
            
            producto.setCategorias(categoriasGestionadas);
        }
        
        Producto savedProduct = productoRepository.save(producto);
        return productoMapper.toDTO(savedProduct);
    }

    public void deleteProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        productoRepository.delete(producto);
    }

    public ProductoUpdateDTO updateProducto(Long id, ProductoUpdateDTO updateDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        if (updateDTO.getNombre() != null) producto.setNombre(updateDTO.getNombre());
        if (updateDTO.getDescripcion() != null) producto.setDescripcion(updateDTO.getDescripcion());
        if (updateDTO.getPrecio() != null) producto.setPrecio(updateDTO.getPrecio());
        if (updateDTO.getStock() != null) producto.setStock(updateDTO.getStock());
        if (updateDTO.getImagen() != null) producto.setImagen(updateDTO.getImagen());
        
        // Manejar categorías
        if (updateDTO.getCategorias() != null && !updateDTO.getCategorias().isEmpty()) {
            List<Categoria> categoriasGestionadas = updateDTO.getCategorias().stream()
                .map(categoria -> {
                    // Si la categoría tiene ID, buscarla
                    if (categoria.getId() != null) {
                        return categoriaRepository.findById(categoria.getId())
                            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + categoria.getId()));
                    }
                    // Si no tiene ID, buscar por nombre o crear nueva
                    else if (categoria.getNombre() != null) {
                        return categoriaRepository.findByNombre(categoria.getNombre())
                            .orElseGet(() -> {
                                Categoria nuevaCategoria = new Categoria();
                                nuevaCategoria.setNombre(categoria.getNombre());
                                return categoriaRepository.save(nuevaCategoria);
                            });
                    }
                    throw new RuntimeException("Categoría inválida: debe tener ID o nombre");
                })
                .collect(Collectors.toList());
            
            producto.setCategorias(categoriasGestionadas);
        }

        Producto savedProduct = productoRepository.save(producto);
        return productoMapper.toDto(savedProduct);
    }
}
