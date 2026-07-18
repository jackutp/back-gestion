package com.microservicio.baseConocimiento.controller;
import com.microservicio.baseConocimiento.dto.*;
import com.microservicio.baseConocimiento.enums.ModuloOrigen;
import com.microservicio.baseConocimiento.service.BusquedaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/base-conocimiento/busqueda")@RequiredArgsConstructor
public class BusquedaController {
private final BusquedaService busquedaService;
    @PostMapping    public ResponseEntity<ApiResponseDTO<List<SugerenciaDTO>>> buscar(@RequestBody BusquedaRequestDTO request) {
List<SugerenciaDTO> resultados = busquedaService.buscar(request);
        return ResponseEntity.ok(ApiResponseDTO.<List<SugerenciaDTO>>builder()                .exito(true)                .mensaje(resultados.isEmpty() ? "Sin resultados" : resultados.size() + " resultados")                .datos(resultados)                .build());
}    @GetMapping("/sugerir")    public ResponseEntity<ApiResponseDTO<List<SugerenciaDTO>>> sugerirPorContexto(@RequestParam String contexto) {
List<SugerenciaDTO> sugerencias = busquedaService.sugerirPorContexto(contexto);
        return ResponseEntity.ok(ApiResponseDTO.<List<SugerenciaDTO>>builder()                .exito(true).datos(sugerencias).build());
}    @GetMapping("/sugerir/por-modulo")    public ResponseEntity<ApiResponseDTO<List<SugerenciaDTO>>> sugerirPorModulo(@RequestParam ModuloOrigen modulo) {
List<SugerenciaDTO> sugerencias = busquedaService.sugerirPorModulo(modulo);
        return ResponseEntity.ok(ApiResponseDTO.<List<SugerenciaDTO>>builder()                .exito(true).datos(sugerencias).build());
}    @GetMapping("/sugerir/al-cierre-incidente")    public ResponseEntity<ApiResponseDTO<List<SugerenciaDTO>>> sugerirAlCerrarIncidente(            @RequestParam Long incidenteId, @RequestParam String categoria) {
List<SugerenciaDTO> sugerencias = busquedaService.sugerirAlCerrarIncidente(incidenteId, categoria);
        return ResponseEntity.ok(ApiResponseDTO.<List<SugerenciaDTO>>builder()                .exito(true).datos(sugerencias).build());
}
}
