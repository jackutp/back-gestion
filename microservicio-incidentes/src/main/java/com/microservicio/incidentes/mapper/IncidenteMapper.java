// com.microservicio.incidentes.mapper.IncidenteMapper.java
package com.microservicio.incidentes.mapper;

import com.microservicio.incidentes.dto.IncidenteRequestDTO;
import com.microservicio.incidentes.dto.IncidenteResponseDTO;
import com.microservicio.incidentes.entity.Incidente;
import com.microservicio.incidentes.enums.EstadoIncidente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface IncidenteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", expression = "java(defaultEstado())")
    @Mapping(target = "jiraIssueKey", ignore = true)
    @Mapping(target = "jiraIssueUrl", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    Incidente toEntity(IncidenteRequestDTO requestDTO);

    IncidenteResponseDTO toResponseDTO(Incidente incidente);

    @Named("defaultEstado")
    default EstadoIncidente defaultEstado() {
        return EstadoIncidente.PENDIENTE;
    }
}