package com.microservicio.baseConocimiento.service;
import com.microservicio.baseConocimiento.dto.*;
import com.microservicio.baseConocimiento.entity.ArticuloKBS;
import com.microservicio.baseConocimiento.enums.EstadoArticulo;
import com.microservicio.baseConocimiento.enums.ModuloOrigen;
import com.microservicio.baseConocimiento.enums.TipoArticulo;
import com.microservicio.baseConocimiento.exception.RecursoNoEncontradoException;
import com.microservicio.baseConocimiento.repository.ArticuloKBSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ArticuloKBSService {
private final ArticuloKBSRepository repository;
    public List<ArticuloKBS> listarTodos() {
return repository.findAll();
}    public ArticuloKBS obtenerPorId(Long id) {
return repository.findById(id)                .orElseThrow(() -> new RecursoNoEncontradoException("ArtÃculo KBS no encontrado con id: " + id));
}    @Transactional    public ArticuloKBS crear(CrearArticuloDTO dto) {
ArticuloKBS articulo = ArticuloKBS.builder()                .titulo(dto.getTitulo())                .descripcion(dto.getDescripcion())                .solucion(dto.getSolucion())                .tipoArticulo(dto.getTipoArticulo())                .moduloOrigen(dto.getModuloOrigen())                .estado(EstadoArticulo.BORRADOR)                .categoria(dto.getCategoria())                .afectaCocina(dto.getAfectaCocina() != null ? dto.getAfectaCocina() : false)                .afectaSalon(dto.getAfectaSalon() != null ? dto.getAfectaSalon() : false)                .afectaReservas(dto.getAfectaReservas() != null ? dto.getAfectaReservas() : false)                .creadoPor(dto.getCreadoPor())                .build();
        return repository.save(articulo);
}    @Transactional    public ArticuloKBS actualizar(Long id, CrearArticuloDTO dto) {
ArticuloKBS articulo = obtenerPorId(id);
        articulo.setTitulo(dto.getTitulo());
        articulo.setDescripcion(dto.getDescripcion());
        articulo.setSolucion(dto.getSolucion());
        articulo.setTipoArticulo(dto.getTipoArticulo());
        articulo.setCategoria(dto.getCategoria());
        articulo.setAfectaCocina(dto.getAfectaCocina() != null ? dto.getAfectaCocina() : false);
        articulo.setAfectaSalon(dto.getAfectaSalon() != null ? dto.getAfectaSalon() : false);
        articulo.setAfectaReservas(dto.getAfectaReservas() != null ? dto.getAfectaReservas() : false);
        return repository.save(articulo);
}    @Transactional    public ArticuloKBS cambiarEstado(Long id, EstadoArticulo nuevoEstado) {
ArticuloKBS articulo = obtenerPorId(id);
        articulo.setEstado(nuevoEstado);
        return repository.save(articulo);
}    @Transactional    public ArticuloKBS votar(Long id, String tipoVoto) {
ArticuloKBS articulo = obtenerPorId(id);
        if ("LIKE".equalsIgnoreCase(tipoVoto)) {
articulo.setLikes(articulo.getLikes() + 1);
} else if ("DISLIKE".equalsIgnoreCase(tipoVoto)) {
articulo.setDislikes(articulo.getDislikes() + 1);
} else {
throw new IllegalArgumentException("tipoVoto debe ser LIKE o DISLIKE");
}        articulo.setTotalVotos(articulo.getLikes() + articulo.getDislikes());
        articulo.setRating(articulo.getTotalVotos() > 0 ? (double) articulo.getLikes() / articulo.getTotalVotos() * 5.0 : 0.0);
        return repository.save(articulo);
}    @Transactional    public void eliminar(Long id) {
ArticuloKBS articulo = obtenerPorId(id);
        repository.delete(articulo);
}    public ResumenKBSDTO obtenerResumen() {
List<ArticuloKBS> todos = repository.findAll();
        return ResumenKBSDTO.builder()                .totalArticulos(todos.size())                .operativos(todos.stream().filter(a -> a.getTipoArticulo() == TipoArticulo.OPERATIVO).count())                .estrategicos(todos.stream().filter(a -> a.getTipoArticulo() == TipoArticulo.ESTRATEGICO).count())                .crisis(todos.stream().filter(a -> a.getTipoArticulo() == TipoArticulo.CRISIS).count())                .publicados(todos.stream().filter(a -> a.getEstado() == EstadoArticulo.PUBLICADO).count())                .enRevision(todos.stream().filter(a -> a.getEstado() == EstadoArticulo.REVISION_PARES).count())                .obsoletos(todos.stream().filter(a -> a.getEstado() == EstadoArticulo.OBSOLETO).count())                .porIncidencias(todos.stream().filter(a -> a.getModuloOrigen() == ModuloOrigen.INCIDENCIAS).count())                .porCambios(todos.stream().filter(a -> a.getModuloOrigen() == ModuloOrigen.CAMBIOS).count())                .porSolicitudes(todos.stream().filter(a -> a.getModuloOrigen() == ModuloOrigen.SOLICITUDES).count())                .ratingPromedio(todos.stream().mapToDouble(ArticuloKBS::getRating).average().orElse(0.0))                .build();
}
}
