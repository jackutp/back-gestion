package com.microservicio.incidentes.service;

import com.microservicio.incidentes.dto.AlertDTO;
import com.microservicio.incidentes.dto.AlertManagerRequest;
import com.microservicio.incidentes.dto.IncidenteRequestDTO;
import com.microservicio.incidentes.enums.Impacto;
import com.microservicio.incidentes.enums.Prioridad;
import com.microservicio.incidentes.enums.TipoAreaAfectada;
import com.microservicio.incidentes.enums.Urgencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertManagerService {
    private final IncidenteService incidenteService;

    private IncidenteRequestDTO convert(AlertDTO alert){
        String alertName = alert.getLabels().get("alertname");
        Prioridad prioridad;
        Urgencia urgencia;
        Impacto impacto;
        TipoAreaAfectada area;

        switch(alertName){
            case "ServiceDown":
                prioridad = Prioridad.CRITICO;
                urgencia = Urgencia.CRITICO;
                impacto = Impacto.EXTENSO_GENERALIZADO;
                area = TipoAreaAfectada.INFRAESTRUCTURA;
                break;

            case "HighCPUCritical":
                prioridad = Prioridad.ALTO;
                urgencia = Urgencia.ALTO;
                impacto = Impacto.SIGNIFICATIVO_GRANDE;
                area = TipoAreaAfectada.INFRAESTRUCTURA;
                break;

            case "HighCPUWarning":
                prioridad = Prioridad.MEDIO;
                urgencia = Urgencia.MEDIO;
                impacto = Impacto.MODERADO_LIMITADO;
                area = TipoAreaAfectada.INFRAESTRUCTURA;
                break;

            case "HighMemoryCritical":
                prioridad = Prioridad.ALTO;
                urgencia = Urgencia.ALTO;
                impacto = Impacto.SIGNIFICATIVO_GRANDE;
                area = TipoAreaAfectada.INFRAESTRUCTURA;
                break;

            case "HighMemoryWarning":
                prioridad = Prioridad.MEDIO;
                urgencia = Urgencia.MEDIO;
                impacto = Impacto.MODERADO_LIMITADO;
                area = TipoAreaAfectada.INFRAESTRUCTURA;
                break;

            case "HighErrorRate":
                prioridad = Prioridad.CRITICO;
                urgencia = Urgencia.ALTO;
                impacto = Impacto.SIGNIFICATIVO_GRANDE;
                area = TipoAreaAfectada.APLICACIONES;
                break;

            case "SlowRequests":
                prioridad = Prioridad.MEDIO;
                urgencia = Urgencia.MEDIO;
                impacto = Impacto.MODERADO_LIMITADO;
                area = TipoAreaAfectada.REDES_COMUNICACIONES;
                break;

            case "DiskAlmostFull":
                prioridad = Prioridad.ALTO;
                urgencia = Urgencia.MEDIO;
                impacto = Impacto.SIGNIFICATIVO_GRANDE;
                area = TipoAreaAfectada.INFRAESTRUCTURA;
                break;

            default:
                throw new IllegalArgumentException("Alerta desconocida: " + alertName);

        }

        return IncidenteRequestDTO.builder()
                .nombreUsuario("Prometheus")
                .resumenProblema(alert.getAnnotations().get("summary"))
                .descripcionDetallada(alert.getAnnotations().get("description"))
                .tipoAreaAfectada(area)
                .prioridad(prioridad)
                .urgencia(urgencia)
                .impacto(impacto)
                .build();
    }

    public void process(AlertManagerRequest request){
        for(AlertDTO alert : request.getAlerts()){
            IncidenteRequestDTO incidente = convert(alert);
            incidenteService.crearIncidente(incidente);
        }
    }
}
