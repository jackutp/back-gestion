// com.microservicio.incidentes.dto.IncidenteResponseDTO.java
package com.microservicio.incidentes.dto;

import com.microservicio.incidentes.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidenteResponseDTO {
    private Long id;
    private String nombreUsuario;
    private String resumenProblema;
    private String descripcionDetallada;
    private TipoAreaAfectada tipoAreaAfectada;
    private Urgencia urgencia;
    private Impacto impacto;
    private Prioridad prioridad;
    private EstadoIncidente estado;
    private String jiraIssueKey;
    private String jiraIssueUrl;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
