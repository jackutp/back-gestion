// com.microservicio.incidentes.entity.Incidente.java
package com.microservicio.incidentes.entity;

import com.microservicio.incidentes.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidentes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;

    @Column(name = "resumen_problema", nullable = false, length = 200)
    private String resumenProblema;

    @Column(name = "descripcion_detallada", nullable = false, columnDefinition = "TEXT")
    private String descripcionDetallada;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_area_afectada", nullable = false)
    private TipoAreaAfectada tipoAreaAfectada;

    @Enumerated(EnumType.STRING)
    @Column(name = "urgencia", nullable = false)
    private Urgencia urgencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "impacto", nullable = false)
    private Impacto impacto;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false)
    private Prioridad prioridad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoIncidente estado;

    @Column(name = "jira_issue_key")
    private String jiraIssueKey;

    @Column(name = "jira_issue_url")
    private String jiraIssueUrl;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoIncidente.PENDIENTE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}