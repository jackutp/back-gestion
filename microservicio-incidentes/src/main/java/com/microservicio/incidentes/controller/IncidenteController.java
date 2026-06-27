// com.microservicio.incidentes.controller.IncidenteController.java
package com.microservicio.incidentes.controller;

import com.microservicio.incidentes.dto.ActualizarEstadoDTO;
import com.microservicio.incidentes.dto.IncidenteRequestDTO;
import com.microservicio.incidentes.dto.IncidenteResponseDTO;
import com.microservicio.incidentes.enums.EstadoIncidente;
import com.microservicio.incidentes.mapper.IncidenteMapper;
import com.microservicio.incidentes.service.IncidenteService;
import com.microservicio.incidentes.service.JiraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidentes")
@RequiredArgsConstructor
public class IncidenteController {

    private final IncidenteService incidenteService;
    private final JiraService jiraService;
    //test
    @GetMapping("/diagnostico/jira-types")
    public ResponseEntity<String> listarRequestTypes() {
        String resultado = jiraService.listarRequestTypes().block();
        return ResponseEntity.ok(resultado);
    }

    //test

    @PostMapping("/create")
    public ResponseEntity<IncidenteResponseDTO> crearIncidente(@Valid @RequestBody IncidenteRequestDTO requestDTO) {
        IncidenteResponseDTO response = incidenteService.crearIncidente(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<IncidenteResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEstadoDTO actualizarEstadoDTO) {
        IncidenteResponseDTO response = incidenteService.actualizarEstado(id, actualizarEstadoDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidenteResponseDTO> obtenerIncidente(@PathVariable Long id) {
        IncidenteResponseDTO response = incidenteService.obtenerIncidente(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<IncidenteResponseDTO>> obtenerTodosLosIncidentes() {
        List<IncidenteResponseDTO> response = incidenteService.obtenerTodosLosIncidentes();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<IncidenteResponseDTO>> obtenerIncidentesPorEstado(@PathVariable EstadoIncidente estado) {
        List<IncidenteResponseDTO> response = incidenteService.obtenerIncidentesPorEstado(estado);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{nombreUsuario}")
    public ResponseEntity<List<IncidenteResponseDTO>> obtenerIncidentesPorUsuario(@PathVariable String nombreUsuario) {
        List<IncidenteResponseDTO> response = incidenteService.obtenerIncidentesPorUsuario(nombreUsuario);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIncidente(@PathVariable Long id) {
        incidenteService.eliminarIncidente(id);
        return ResponseEntity.noContent().build();
    }
}