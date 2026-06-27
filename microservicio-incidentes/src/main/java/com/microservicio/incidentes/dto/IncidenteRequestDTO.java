// com.microservicio.incidentes.dto.IncidenteRequestDTO.java
package com.microservicio.incidentes.dto;

import com.microservicio.incidentes.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidenteRequestDTO {

    @NotBlank(message = "El nombre de usuario es requerido")
    private String nombreUsuario;

    @NotBlank(message = "El resumen del problema es requerido")
    private String resumenProblema;

    @NotBlank(message = "La descripción detallada es requerida")
    private String descripcionDetallada;

    @NotNull(message = "El tipo de área afectada es requerido")
    private TipoAreaAfectada tipoAreaAfectada;

    @NotNull(message = "La urgencia es requerida")
    private Urgencia urgencia;

    @NotNull(message = "El impacto es requerido")
    private Impacto impacto;

    @NotNull(message = "La prioridad es requerida")
    private Prioridad prioridad;
}