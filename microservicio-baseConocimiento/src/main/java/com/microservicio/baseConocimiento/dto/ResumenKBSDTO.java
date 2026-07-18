package com.microservicio.baseConocimiento.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumenKBSDTO {
private long totalArticulos;
    private long operativos;
    private long estrategicos;
    private long crisis;
    private long publicados;
    private long enRevision;
    private long obsoletos;
    private long porIncidencias;
    private long porCambios;
    private long porSolicitudes;
    private double ratingPromedio;
}
