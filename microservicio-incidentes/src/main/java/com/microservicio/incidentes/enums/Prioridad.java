// com.microservicio.incidentes.enums.Prioridad.java
package com.microservicio.incidentes.enums;

public enum Prioridad {
    CRITICO("Crítico"),
    ALTO("Alto"),
    MEDIO("Medio"),
    BAJO("Bajo");

    private final String valor;

    Prioridad(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}