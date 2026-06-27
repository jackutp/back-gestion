// com.microservicio.incidentes.repository.IncidenteRepository.java
package com.microservicio.incidentes.repository;

import com.microservicio.incidentes.entity.Incidente;
import com.microservicio.incidentes.enums.EstadoIncidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente, Long> {
    List<Incidente> findByEstado(EstadoIncidente estado);
    List<Incidente> findByNombreUsuario(String nombreUsuario);
}