package com.microservicio.baseConocimiento.service;
import com.microservicio.baseConocimiento.dto.BusquedaRequestDTO;
import com.microservicio.baseConocimiento.dto.SugerenciaDTO;
import com.microservicio.baseConocimiento.entity.ArticuloKBS;
import com.microservicio.baseConocimiento.enums.ModuloOrigen;
import com.microservicio.baseConocimiento.repository.ArticuloKBSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
@RequiredArgsConstructor
public class BusquedaService {
private final ArticuloKBSRepository repository;
    public List<SugerenciaDTO> buscar(BusquedaRequestDTO request) {
String query = request.getQuery() != null ? request.getQuery().toLowerCase() : null;
        List<ArticuloKBS> resultados = repository.buscarArticulos(                query, request.getCategoria(),                request.getAfectaCocina(), request.getAfectaSalon(), request.getAfectaReservas());
        if (request.getModuloOrigen() != null) {
resultados = resultados.stream()                    .filter(a -> a.getModuloOrigen() == request.getModuloOrigen())                    .collect(Collectors.toList());
}        Stream<SugerenciaDTO> sugerencias = resultados.stream().map(this::mapearSugerencia);
        if (query != null) {
sugerencias = sugerencias.sorted((a, b) -> {
double relevanciaA = calcularRelevancia(a, query);
                double relevanciaB = calcularRelevancia(b, query);
                return Double.compare(relevanciaB, relevanciaA);
});
}        List<SugerenciaDTO> lista = sugerencias.collect(Collectors.toList());
        int limit = request.getLimit() != null ? request.getLimit() : 10;
        return lista.size() > limit ? lista.subList(0, limit) : lista;
}    public List<SugerenciaDTO> sugerirPorContexto(String contexto) {
String contextoLower = contexto.toLowerCase();
        boolean mencionaCocina = contextoLower.contains("horno") || contextoLower.contains("cocina")                || contextoLower.contains("coccion") || contextoLower.contains("temperatura");
        boolean mencionaSalon = contextoLower.contains("comanda") || contextoLower.contains("pedido")                || contextoLower.contains("mesa") || contextoLower.contains("salon")                || contextoLower.contains("pantalla");
        boolean mencionaReservas = contextoLower.contains("reserva") || contextoLower.contains("comensal")                || contextoLower.contains("cliente");
        List<ArticuloKBS> resultados = repository.buscarArticulos(                contexto, null,                mencionaCocina ? true : null,                mencionaSalon ? true : null,                mencionaReservas ? true : null);
        return resultados.stream()                .map(this::mapearSugerencia)                .sorted((a, b) -> {
double pesoA = 0;
                    if (mencionaCocina && "COMANDAS".equalsIgnoreCase(a.getCategoria())) pesoA += 3;
                    if (mencionaSalon && "COMANDAS".equalsIgnoreCase(a.getCategoria())) pesoA += 3;
                    if (mencionaReservas && "RESERVAS".equalsIgnoreCase(a.getCategoria())) pesoA += 3;
                    double pesoB = 0;
                    if (mencionaCocina && "COMANDAS".equalsIgnoreCase(b.getCategoria())) pesoB += 3;
                    if (mencionaSalon && "COMANDAS".equalsIgnoreCase(b.getCategoria())) pesoB += 3;
                    if (mencionaReservas && "RESERVAS".equalsIgnoreCase(b.getCategoria())) pesoB += 3;
                    return Double.compare(pesoB + b.getLikes(), pesoA + a.getLikes());
})                .limit(5)                .collect(Collectors.toList());
}    public List<SugerenciaDTO> sugerirAlCerrarIncidente(Long incidenteId, String categoria) {
List<ArticuloKBS> articulos = repository.buscarArticulos(null, categoria, null, null, null);
        return articulos.stream()                .map(this::mapearSugerencia)                .sorted(Comparator.comparingDouble(SugerenciaDTO::getRelevancia).reversed())                .limit(3)                .collect(Collectors.toList());
}    public List<SugerenciaDTO> sugerirPorModulo(ModuloOrigen modulo) {
List<ArticuloKBS> articulos = repository.sugerirPorModulo(modulo);
        return articulos.stream()                .map(this::mapearSugerencia)                .limit(5)                .collect(Collectors.toList());
}    private double calcularRelevancia(SugerenciaDTO s, String query) {
double peso = 0;
        String titulo = s.getTitulo() != null ? s.getTitulo().toLowerCase() : "";
        String descripcion = s.getDescripcion() != null ? s.getDescripcion().toLowerCase() : "";
        if (titulo.contains(query)) peso += 10;
        if (descripcion.contains(query)) peso += 5;
        peso += (s.getLikes() != null ? s.getLikes() : 0) * 0.5;
        peso += s.getRating();
        return peso;
}    private SugerenciaDTO mapearSugerencia(ArticuloKBS articulo) {
return SugerenciaDTO.builder()                .id(articulo.getId())                .titulo(articulo.getTitulo())                .descripcion(articulo.getDescripcion())                .categoria(articulo.getCategoria())                .rating(articulo.getRating())                .likes(articulo.getLikes())                .dislikes(articulo.getDislikes())                .moduloOrigen(articulo.getModuloOrigen())                .afectaCocina(articulo.getAfectaCocina())                .afectaSalon(articulo.getAfectaSalon())                .afectaReservas(articulo.getAfectaReservas())                .build();
}
}
