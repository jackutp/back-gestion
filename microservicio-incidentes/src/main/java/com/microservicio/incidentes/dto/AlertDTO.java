package com.microservicio.incidentes.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AlertDTO {
    private String status;
    private Map<String, String> labels;
    private Map<String, String> annotations;
}
