package com.api.e_commerce.service;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.e_commerce.dto.DetallePedidoDTO;
import com.api.e_commerce.dto.DireccionDTO;
import com.api.e_commerce.dto.PedidoRequestDTO;
import com.api.e_commerce.dto.PedidoResponseDTO;
import com.api.e_commerce.exception.PedidoNotFoundException;
import com.api.e_commerce.exception.ProductoNotFoundException;
import com.api.e_commerce.exception.UsuarioNotFoundException;
import com.api.e_commerce.model.DetallePedido;
import com.api.e_commerce.model.Direccion;
import com.api.e_commerce.model.Pedido;
import com.api.e_commerce.model.Producto;
import com.api.e_commerce.model.Usuario;
import com.api.e_commerce.repository.PedidoRepository;
import com.api.e_commerce.repository.ProductoRepository;
import com.api.e_commerce.repository.UsuarioRepository;

@Service
@Transactional
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido createPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
    
    public PedidoResponseDTO createPedidoFromRequest(PedidoRequestDTO request) {
        // Validar y obtener usuario
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
            .orElseThrow(() -> new UsuarioNotFoundException(request.getUsuarioId()));
        
        // Crear dirección
        Direccion direccion = Direccion.builder()
            .calle(request.getDireccion().getCalle())
            .numero(request.getDireccion().getNumero())
            .localidad(request.getDireccion().getLocalidad())
            .provincia(request.getDireccion().getProvincia())
            .pais(request.getDireccion().getPais())
            .codigoPostal(request.getDireccion().getCodigoPostal())
            .usuario(usuario)
            .build();
        
        // Crear pedido
        Pedido pedido = Pedido.builder()
            .fecha(LocalDateTime.now())
            .estado("CONFIRMADO")
            .usuario(usuario)
            .direccion(direccion)
            .build();
        
        // Calcular total y crear detalles
        double total = 0.0;
        for (DetallePedidoDTO detalleDTO : request.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                .orElseThrow(() -> new ProductoNotFoundException(detalleDTO.getProductoId()));
            
            double subtotal = detalleDTO.getCantidad() * detalleDTO.getPrecioUnitario();
            total += subtotal;
            
            DetallePedido detalle = DetallePedido.builder()
                .producto(producto)
                .cantidad(detalleDTO.getCantidad())
                .precioUnitario(detalleDTO.getPrecioUnitario())
                .subtotal(subtotal)
                .build();
            
            pedido.addDetalle(detalle);
        }
        
        pedido.setTotal(total);
        
        // Guardar pedido (cascada guarda detalles y dirección)
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        
        // Convertir a DTO de respuesta
        return convertirAPedidoResponseDTO(pedidoGuardado, request);
    }
    
    private PedidoResponseDTO convertirAPedidoResponseDTO(Pedido pedido, PedidoRequestDTO request) {
        List<DetallePedidoDTO> detallesDTO = pedido.getDetalles().stream()
            .map(detalle -> DetallePedidoDTO.builder()
                .productoId(detalle.getProducto().getId())
                .nombreProducto(detalle.getProducto().getNombre())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .build())
            .collect(Collectors.toList());
        
        DireccionDTO direccionDTO = DireccionDTO.builder()
            .calle(pedido.getDireccion().getCalle())
            .numero(pedido.getDireccion().getNumero())
            .localidad(pedido.getDireccion().getLocalidad())
            .provincia(pedido.getDireccion().getProvincia())
            .pais(pedido.getDireccion().getPais())
            .codigoPostal(pedido.getDireccion().getCodigoPostal())
            .build();
        
        return PedidoResponseDTO.builder()
            .id(pedido.getId())
            .fecha(pedido.getFecha())
            .estado(pedido.getEstado())
            .total(pedido.getTotal())
            .nombreCliente(request.getNombre() + " " + request.getApellido())
            .emailCliente(request.getEmail())
            .direccion(direccionDTO)
            .detalles(detallesDTO)
            .build();
    }

    public Pedido getPedidoById(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
    }  

    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }
    
}
