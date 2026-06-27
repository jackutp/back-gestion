// com.microservicio.incidentes.service.JiraService.java
package com.microservicio.incidentes.service;

import com.microservicio.incidentes.dto.JiraIssueDTO;
import com.microservicio.incidentes.entity.Incidente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class JiraService {

    private final WebClient webClient;

    @Value("${jira.project.key}")
    private String projectKey;

    @Value("${jira.request.type.id:8}")
    private String requestTypeId;

    // ─────────────────────────────────────────────
    // MÉTODO PRINCIPAL
    // ─────────────────────────────────────────────

    public Mono<JiraIssueDTO.IssueResponse> crearIncidente(Incidente incidente) {
        log.info("========================================");
        log.info("🚀 Creando incidente en Jira Service Management");
        log.info("   Usuario : {}", incidente.getNombreUsuario());
        log.info("   Resumen : {}", incidente.getResumenProblema());
        log.info("========================================");

        return getServiceDeskId()
                .flatMap(serviceDeskId -> {
                    log.info("✅ Service Desk ID     : {}", serviceDeskId);
                    log.info("✅ Request Type ID (fijo): {}", requestTypeId);
                    return createServiceDeskRequest(serviceDeskId, requestTypeId, incidente);
                })
                .doOnSuccess(response -> {
                    log.info("========================================");
                    log.info("✅ INCIDENTE CREADO EN JIRA");
                    log.info("   ID      : {}", response.getId());
                    log.info("   Key     : {}", response.getKey());
                    log.info("   IssueKey: {}", response.getIssueKey());
                    log.info("   URL     : {}", response.getSelf());
                    log.info("========================================");
                })
                .doOnError(error -> {
                    log.error("========================================");
                    log.error("❌ ERROR AL CREAR INCIDENTE EN JIRA");
                    log.error("   Mensaje: {}", error.getMessage());
                    if (error instanceof WebClientResponseException wcre) {
                        log.error("   Status : {}", wcre.getStatusCode());
                        log.error("   Body   : {}", wcre.getResponseBodyAsString());
                    }
                    log.error("========================================");
                });
    }

    // ─────────────────────────────────────────────
    // OBTENER SERVICE DESK ID
    // ─────────────────────────────────────────────

    private Mono<String> getServiceDeskId() {
        log.info("🔍 Buscando Service Desk ID para project key: {}", projectKey);

        return webClient.get()
                .uri("/rest/servicedeskapi/servicedesk")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var values = (java.util.List<Map<String, Object>>) response.get("values");
                    if (values != null) {
                        for (Map<String, Object> desk : values) {
                            String deskProjectKey = (String) desk.get("projectKey");
                            if (projectKey.equals(deskProjectKey)) {
                                String deskId = desk.get("id").toString();
                                log.info("✅ Service Desk encontrado: ID={}", deskId);
                                return deskId;
                            }
                        }
                    }
                    log.warn("⚠️ Service Desk no encontrado, usando projectKey como fallback: {}", projectKey);
                    return projectKey;
                });
    }

    // ─────────────────────────────────────────────
    // CREAR SOLICITUD EN JIRA
    // ─────────────────────────────────────────────

    private Mono<JiraIssueDTO.IssueResponse> createServiceDeskRequest(
            String serviceDeskId,
            String requestTypeId,
            Incidente incidente) {

        log.info("📝 Construyendo payload para Jira...");

        Map<String, Object> requestFieldValues = new HashMap<>();
        requestFieldValues.put("summary", incidente.getResumenProblema());
        requestFieldValues.put("description", incidente.getDescripcionDetallada());

        Map<String, Object> body = new HashMap<>();
        body.put("serviceDeskId", serviceDeskId);
        body.put("requestTypeId", requestTypeId);
        body.put("requestFieldValues", requestFieldValues);

        log.debug("Payload: {}", body);

        return webClient.post()
                .uri("/rest/servicedeskapi/request")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JiraIssueDTO.IssueResponse.class)
                .doOnSuccess(response -> {
                    log.info("✅ Respuesta Jira: ID={}, Key={}, IssueKey={}, URL={}",
                            response.getId(), response.getKey(),
                            response.getIssueKey(), response.getSelf());
                })
                .doOnError(error -> {
                    log.error("❌ Error en respuesta Jira: {}", error.getMessage());
                    if (error instanceof WebClientResponseException wcre) {
                        log.error("   Body: {}", wcre.getResponseBodyAsString());
                    }
                });
    }

    // ─────────────────────────────────────────────
    // DIAGNÓSTICO (puedes eliminarlo luego)
    // ─────────────────────────────────────────────

    public Mono<String> listarRequestTypes() {
        return getServiceDeskId()
                .flatMap(serviceDeskId ->
                        webClient.get()
                                .uri("/rest/servicedeskapi/servicedesk/{id}/requesttype", serviceDeskId)
                                .retrieve()
                                .bodyToMono(Map.class)
                                .map(response -> {
                                    var values = (java.util.List<Map<String, Object>>) response.get("values");
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("Service Desk ID: ").append(serviceDeskId).append("\n");
                                    if (values != null) {
                                        values.forEach(type ->
                                                sb.append("ID: ").append(type.get("id"))
                                                        .append(" | Name: ").append(type.get("name"))
                                                        .append(" | Group: ").append(type.get("groupIds"))
                                                        .append("\n")
                                        );
                                    }
                                    return sb.toString();
                                })
                );
    }
}