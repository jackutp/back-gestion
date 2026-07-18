package com.microservicio.baseConocimiento.dto;
import com.microservicio.baseConocimiento.enums.EstadoArticulo;
import com.microservicio.baseConocimiento.enums.ModuloOrigen;
import com.microservicio.baseConocimiento.enums.TipoArticulo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloKBSDTO {
private Long id;
    private String titulo;
    private String descripcion;
    private String solucion;
    private TipoArticulo tipoArticulo;
    private ModuloOrigen moduloOrigen;
    private EstadoArticulo estado;
    private String categoria;
    private Double rating;
    private Integer totalVotos;
    private Integer likes;
    private Integer dislikes;
    private Boolean afectaCocina;
    private Boolean afectaSalon;
    private Boolean afectaReservas;
    private String creadoPor;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
