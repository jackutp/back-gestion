package com.microservicio.baseConocimiento.dto;
import com.microservicio.baseConocimiento.enums.ModuloOrigen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusquedaRequestDTO {
private String query;
    private Boolean afectaCocina;
    private Boolean afectaSalon;
    private Boolean afectaReservas;
    private String categoria;
    private ModuloOrigen moduloOrigen;
    private Integer limit;
}
