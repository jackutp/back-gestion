// com.microservicio.incidentes.enums.TipoAreaAfectada.java
package com.microservicio.incidentes.enums;

public enum TipoAreaAfectada {
    INFRAESTRUCTURA("Infraestructura"),
    APLICACIONES("Aplicaciones"),
    BASE_DATOS("Base de datos"),
    REDES_COMUNICACIONES("Redes y comunicaciones"),
    SEGURIDAD("Seguridad"),
    DOCUMENTACION("Documentación");

    private final String descripcion;

    TipoAreaAfectada(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}