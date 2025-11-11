package com.api.e_commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {
    private Long usuarioId;
    private List<DetallePedidoDTO> detalles;
    private DireccionDTO direccion;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
}
