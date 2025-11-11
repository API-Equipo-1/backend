package com.api.e_commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;
    private String pais;
    private String codigoPostal;
}
