package com.microservicio.baseConocimiento.controller;
import com.microservicio.baseConocimiento.dto.*;
import com.microservicio.baseConocimiento.entity.ArticuloKBS;
import com.microservicio.baseConocimiento.enums.EstadoArticulo;
import com.microservicio.baseConocimiento.service.ArticuloKBSService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/base-conocimiento")@RequiredArgsConstructor
public class ArticuloKBSController {
private final ArticuloKBSService articuloService;
    @GetMapping    public ResponseEntity<ApiResponseDTO<List<ArticuloKBSDTO>>> listarTodos() {
List<ArticuloKBSDTO> articulos = articuloService.listarTodos().stream().map(this::mapearDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponseDTO.<List<ArticuloKBSDTO>>builder().exito(true).mensaje("Articulos encontrados: " + articulos.size()).datos(articulos).build());
}    @GetMapping("/{id}")    public ResponseEntity<ApiResponseDTO<ArticuloKBSDTO>> obtenerPorId(@PathVariable Long id) {
ArticuloKBS articulo = articuloService.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.<ArticuloKBSDTO>builder().exito(true).datos(mapearDTO(articulo)).build());
}    @PostMapping    public ResponseEntity<ApiResponseDTO<ArticuloKBSDTO>> crear(@Valid @RequestBody CrearArticuloDTO dto) {
ArticuloKBS articulo = articuloService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.<ArticuloKBSDTO>builder().exito(true).mensaje("Articulos creado correctamente").datos(mapearDTO(articulo)).build());
}    @PutMapping("/{id}")    public ResponseEntity<ApiResponseDTO<ArticuloKBSDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody CrearArticuloDTO dto) {
ArticuloKBS articulo = articuloService.actualizar(id, dto);
        return ResponseEntity.ok(ApiResponseDTO.<ArticuloKBSDTO>builder().exito(true).mensaje("Articulos actualizado").datos(mapearDTO(articulo)).build());
}    @PatchMapping("/{id}/estado")    public ResponseEntity<ApiResponseDTO<ArticuloKBSDTO>> cambiarEstado(@PathVariable Long id, @RequestParam EstadoArticulo estado) {
ArticuloKBS articulo = articuloService.cambiarEstado(id, estado);
        return ResponseEntity.ok(ApiResponseDTO.<ArticuloKBSDTO>builder().exito(true).mensaje("Estado cambiado a: " + estado).datos(mapearDTO(articulo)).build());
}    @PostMapping("/{id}/votar")    public ResponseEntity<ApiResponseDTO<ArticuloKBSDTO>> votar(@PathVariable Long id, @RequestParam String tipo) {
ArticuloKBS articulo = articuloService.votar(id, tipo);
        return ResponseEntity.ok(ApiResponseDTO.<ArticuloKBSDTO>builder().exito(true).mensaje("Voto registrado: " + tipo).datos(mapearDTO(articulo)).build());
}    @GetMapping("/resumen")    public ResponseEntity<ApiResponseDTO<ResumenKBSDTO>> obtenerResumen() {
ResumenKBSDTO resumen = articuloService.obtenerResumen();
        return ResponseEntity.ok(ApiResponseDTO.<ResumenKBSDTO>builder().exito(true).datos(resumen).build());
}    @DeleteMapping("/{id}")    public ResponseEntity<ApiResponseDTO<Void>> eliminar(@PathVariable Long id) {
articuloService.eliminar(id);
        return ResponseEntity.ok(ApiResponseDTO.<Void>builder().exito(true).mensaje("Articulos eliminado").build());
}    private ArticuloKBSDTO mapearDTO(ArticuloKBS articulo) {
return ArticuloKBSDTO.builder().id(articulo.getId()).titulo(articulo.getTitulo()).descripcion(articulo.getDescripcion()).solucion(articulo.getSolucion())                .tipoArticulo(articulo.getTipoArticulo())                .moduloOrigen(articulo.getModuloOrigen())                .estado(articulo.getEstado())                .categoria(articulo.getCategoria())                .rating(articulo.getRating())                .totalVotos(articulo.getTotalVotos())                .likes(articulo.getLikes())                .dislikes(articulo.getDislikes())                .afectaCocina(articulo.getAfectaCocina())                .afectaSalon(articulo.getAfectaSalon())                .afectaReservas(articulo.getAfectaReservas())                .creadoPor(articulo.getCreadoPor())                .fechaCreacion(articulo.getFechaCreacion())                .fechaActualizacion(articulo.getFechaActualizacion())                .build();
}
}
