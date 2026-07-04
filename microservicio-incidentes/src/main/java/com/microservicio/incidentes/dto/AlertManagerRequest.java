package com.microservicio.incidentes.dto;

import lombok.Data;

import java.util.List;

@Data
public class AlertManagerRequest {
    private List<AlertDTO> alerts;
}
