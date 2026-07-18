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
public class SugerenciaDTO {
private Long id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private Double relevancia;
    private Double rating;
    private Integer likes;
    private Integer dislikes;
    private ModuloOrigen moduloOrigen;
    private Boolean afectaCocina;
    private Boolean afectaSalon;
    private Boolean afectaReservas;
}
