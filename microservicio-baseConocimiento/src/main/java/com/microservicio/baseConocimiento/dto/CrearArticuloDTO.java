package com.microservicio.baseConocimiento.dto;
import com.microservicio.baseConocimiento.enums.ModuloOrigen;
import com.microservicio.baseConocimiento.enums.TipoArticulo;
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
public class CrearArticuloDTO {
@NotBlank    private String titulo;
    @NotBlank    private String descripcion;
    private String solucion;
    @NotNull    private TipoArticulo tipoArticulo;
    @NotNull    private ModuloOrigen moduloOrigen;
    private String categoria;
    private Boolean afectaCocina;
    private Boolean afectaSalon;
    private Boolean afectaReservas;
    private String creadoPor;
}
