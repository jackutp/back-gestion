// com.microservicio.incidentes.enums.Impacto.java
package com.microservicio.incidentes.enums;

public enum Impacto {
    EXTENSO_GENERALIZADO("Extenso / Generalizado"),
    SIGNIFICATIVO_GRANDE("Significativo / Grande"),
    MODERADO_LIMITADO("Moderado / Limitado"),
    MENOR_LOCALIZADO("Menor / Localizado");

    private final String valor;

    Impacto(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}