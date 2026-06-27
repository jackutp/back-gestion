// com.microservicio.incidentes.enums.Urgencia.java
package com.microservicio.incidentes.enums;

public enum Urgencia {
    CRITICO("Crítico"),
    ALTO("Alto"),
    MEDIO("Medio"),
    BAJO("Bajo");

    private final String valor;

    Urgencia(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}