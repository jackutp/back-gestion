package com.microservicio.baseConocimiento.repository;
import com.microservicio.baseConocimiento.entity.ArticuloKBS;
import com.microservicio.baseConocimiento.enums.EstadoArticulo;
import com.microservicio.baseConocimiento.enums.ModuloOrigen;
import com.microservicio.baseConocimiento.enums.TipoArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ArticuloKBSRepository extends JpaRepository<ArticuloKBS, Long> {
List<ArticuloKBS> findByEstado(EstadoArticulo estado);
    List<ArticuloKBS> findByModuloOrigen(ModuloOrigen moduloOrigen);
    List<ArticuloKBS> findByTipoArticulo(TipoArticulo tipoArticulo);
    @Query("SELECT a FROM ArticuloKBS a WHERE " +           "(:query IS NULL OR LOWER(a.titulo) LIKE LOWER(CONCAT('%', :query, '%')) " +           "OR LOWER(a.descripcion) LIKE LOWER(CONCAT('%', :query, '%')) " +           "OR LOWER(a.solucion) LIKE LOWER(CONCAT('%', :query, '%'))) " +           "AND (:categoria IS NULL OR a.categoria = :categoria) " +           "AND (:afectaCocina IS NULL OR a.afectaCocina = :afectaCocina) " +           "AND (:afectaSalon IS NULL OR a.afectaSalon = :afectaSalon) " +           "AND (:afectaReservas IS NULL OR a.afectaReservas = :afectaReservas) " +           "AND a.estado = 'PUBLICADO' " +           "ORDER BY a.likes DESC, a.rating DESC")    List<ArticuloKBS> buscarArticulos(@Param("query") String query,                                       @Param("categoria") String categoria,                                       @Param("afectaCocina") Boolean afectaCocina,                                       @Param("afectaSalon") Boolean afectaSalon,                                       @Param("afectaReservas") Boolean afectaReservas);
    @Query("SELECT a FROM ArticuloKBS a WHERE a.estado = 'PUBLICADO' " +           "AND (:moduloOrigen IS NULL OR a.moduloOrigen = :moduloOrigen) " +           "ORDER BY a.likes DESC, a.rating DESC")    List<ArticuloKBS> sugerirPorModulo(@Param("moduloOrigen") ModuloOrigen moduloOrigen);
    @Query("SELECT a FROM ArticuloKBS a WHERE a.estado = 'OBSOLETO' " +           "AND a.fechaActualizacion < :fechaLimite")    List<ArticuloKBS> articulosObsoletosParaArchivar(@Param("fechaLimite") LocalDateTime fechaLimite);
}
