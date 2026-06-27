// com.microservicio.incidentes.dto.ActualizarEstadoDTO.java
package com.microservicio.incidentes.dto;

import com.microservicio.incidentes.enums.EstadoIncidente;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstadoDTO {

    @NotNull(message = "El estado es requerido")
    private EstadoIncidente estado;
}