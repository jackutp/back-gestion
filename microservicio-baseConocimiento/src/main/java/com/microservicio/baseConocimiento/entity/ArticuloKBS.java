package com.microservicio.baseConocimiento.entity;
import com.microservicio.baseConocimiento.enums.EstadoArticulo;
import com.microservicio.baseConocimiento.enums.ModuloOrigen;
import com.microservicio.baseConocimiento.enums.TipoArticulo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Entity
@Table(name = "articulo_kbs")@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloKBS {
@Id    @GeneratedValue(strategy = GenerationType.IDENTITY)    private Long id;
    @Column(nullable = false, length = 200)    private String titulo;
    @Column(nullable = false, columnDefinition = "TEXT")    private String descripcion;
    @Column(columnDefinition = "TEXT")    private String solucion;
    @Enumerated(EnumType.STRING)    @Column(nullable = false, length = 20)    private TipoArticulo tipoArticulo;
    @Enumerated(EnumType.STRING)    @Column(nullable = false, length = 20)    private ModuloOrigen moduloOrigen;
    @Enumerated(EnumType.STRING)    @Column(nullable = false, length = 20)    private EstadoArticulo estado;
    @Column(length = 100)    private String categoria;
    @Column(nullable = false)    @Builder.Default    private Double rating = 0.0;
    @Column(name = "total_votos")    @Builder.Default    private Integer totalVotos = 0;
    @Column(nullable = false)    @Builder.Default    private Integer likes = 0;
    @Column(nullable = false)    @Builder.Default    private Integer dislikes = 0;
    @Column(name = "afecta_cocina")    @Builder.Default    private Boolean afectaCocina = false;
    @Column(name = "afecta_salon")    @Builder.Default    private Boolean afectaSalon = false;
    @Column(name = "afecta_reservas")    @Builder.Default    private Boolean afectaReservas = false;
    @Column(name = "creado_por", length = 100)    private String creadoPor;
    @Column(nullable = false)    private LocalDateTime fechaCreacion;
    @Column(nullable = false)    private LocalDateTime fechaActualizacion;
    @PrePersist    protected void onCreate() {
fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        likes = 0;
        dislikes = 0;
        totalVotos = 0;
        rating = 0.0;
        if (estado == null) {
estado = EstadoArticulo.BORRADOR;
}
}    @PreUpdate    protected void onUpdate() {
fechaActualizacion = LocalDateTime.now();
}
}
