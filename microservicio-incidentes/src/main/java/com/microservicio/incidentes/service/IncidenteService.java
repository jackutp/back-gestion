// com.microservicio.incidentes.service.IncidenteService.java
package com.microservicio.incidentes.service;

import com.microservicio.incidentes.dto.ActualizarEstadoDTO;
import com.microservicio.incidentes.dto.IncidenteRequestDTO;
import com.microservicio.incidentes.dto.IncidenteResponseDTO;
import com.microservicio.incidentes.dto.JiraIssueDTO;
import com.microservicio.incidentes.entity.Incidente;
import com.microservicio.incidentes.enums.EstadoIncidente;
import com.microservicio.incidentes.mapper.IncidenteMapper;
import com.microservicio.incidentes.repository.IncidenteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncidenteService {

    private final IncidenteRepository incidenteRepository;
    private final IncidenteMapper incidenteMapper;
    private final JiraService jiraService;

    @Transactional
    public IncidenteResponseDTO crearIncidente(IncidenteRequestDTO requestDTO) {
        log.info("========================================");
        log.info("📝 INICIANDO CREACIÓN DE INCIDENTE");
        log.info("========================================");
        log.info("Usuario: {}", requestDTO.getNombreUsuario());
        log.info("Resumen: {}", requestDTO.getResumenProblema());
        log.info("========================================");

        // 1. Crear entidad
        Incidente incidente = incidenteMapper.toEntity(requestDTO);
        incidente.setFechaCreacion(LocalDateTime.now());
        incidente.setEstado(EstadoIncidente.PENDIENTE);

        // 2. Guardar primero en BD
        Incidente incidenteGuardado = incidenteRepository.save(incidente);
        log.info("✅ Incidente guardado en BD con ID: {}", incidenteGuardado.getId());

        // 3. Intentar crear en Jira
        try {
            log.info("📤 Enviando a Jira...");
            JiraIssueDTO.IssueResponse jiraResponse = jiraService.crearIncidente(incidenteGuardado).block();

            if (jiraResponse != null) {
                log.info("✅ Respuesta de Jira recibida:");
                log.info("   ID: {}", jiraResponse.getId());
                log.info("   Key: {}", jiraResponse.getKey());
                log.info("   IssueKey: {}", jiraResponse.getIssueKey());
                log.info("   URL: {}", jiraResponse.getSelf());

                // ACTUALIZAR EL INCIDENTE CON LA INFO DE JIRA
                incidenteGuardado.setJiraIssueKey(jiraResponse.getKey());
                incidenteGuardado.setJiraIssueUrl(jiraResponse.getSelf());

                // Guardar los cambios en BD
                incidenteGuardado = incidenteRepository.save(incidenteGuardado);
                log.info("✅ Incidente actualizado con Jira Key: {}", incidenteGuardado.getJiraIssueKey());
            } else {
                log.warn("⚠️ Jira respondió con null, no se actualizó la BD");
            }
        } catch (Exception e) {
            log.error("❌ Error al crear incidente en Jira: {}", e.getMessage());
            log.error("   Causa: {}", e.getCause() != null ? e.getCause().getMessage() : "N/A");
            // El incidente ya está guardado en BD, continuamos
        }

        log.info("========================================");
        log.info("✅ INCIDENTE CREADO");
        log.info("   ID en BD: {}", incidenteGuardado.getId());
        log.info("   Jira Key: {}", incidenteGuardado.getJiraIssueKey());
        log.info("========================================");

        return incidenteMapper.toResponseDTO(incidenteGuardado);
    }

    @Transactional
    public IncidenteResponseDTO actualizarEstado(Long id, ActualizarEstadoDTO actualizarEstadoDTO) {
        Incidente incidente = incidenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado con ID: " + id));

        incidente.setEstado(actualizarEstadoDTO.getEstado());
        incidente.setFechaActualizacion(LocalDateTime.now());

        Incidente incidenteActualizado = incidenteRepository.save(incidente);
        return incidenteMapper.toResponseDTO(incidenteActualizado);
    }

    public IncidenteResponseDTO obtenerIncidente(Long id) {
        Incidente incidente = incidenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado con ID: " + id));
        return incidenteMapper.toResponseDTO(incidente);
    }

    public List<IncidenteResponseDTO> obtenerTodosLosIncidentes() {
        return incidenteRepository.findAll().stream()
                .map(incidenteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<IncidenteResponseDTO> obtenerIncidentesPorEstado(EstadoIncidente estado) {
        return incidenteRepository.findByEstado(estado).stream()
                .map(incidenteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<IncidenteResponseDTO> obtenerIncidentesPorUsuario(String nombreUsuario) {
        return incidenteRepository.findByNombreUsuario(nombreUsuario).stream()
                .map(incidenteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void eliminarIncidente(Long id) {
        if (!incidenteRepository.existsById(id)) {
            throw new RuntimeException("Incidente no encontrado con ID: " + id);
        }
        incidenteRepository.deleteById(id);
    }
}