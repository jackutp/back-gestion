// com.microservicio.incidentes.enums.EstadoIncidente.java
package com.microservicio.incidentes.enums;

public enum EstadoIncidente {
    PENDIENTE("Pendiente - PENDING"),
    INVESTIGAR("Investigar - WORK IN PROGRESS"),
    CANCELAR("Cancelar - CANCELADO"),
    RESOLVER("Resolver - COMPLETADO");

    private final String valor;

    EstadoIncidente(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}