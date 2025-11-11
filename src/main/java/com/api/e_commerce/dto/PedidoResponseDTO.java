package com.api.e_commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private String estado;
    private Double total;
    private String nombreCliente;
    private String emailCliente;
    private DireccionDTO direccion;
    private List<DetallePedidoDTO> detalles;
}
