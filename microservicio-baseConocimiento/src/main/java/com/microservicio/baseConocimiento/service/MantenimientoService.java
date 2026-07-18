package com.microservicio.baseConocimiento.service;
import com.microservicio.baseConocimiento.entity.ArticuloKBS;
import com.microservicio.baseConocimiento.repository.ArticuloKBSRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class MantenimientoService {
private final ArticuloKBSRepository repository;
    @Transactional    public int archivarObsoletos() {
LocalDateTime fechaLimite = LocalDateTime.now().minusYears(1);
        List<ArticuloKBS> paraArchivar = repository.articulosObsoletosParaArchivar(fechaLimite);
        repository.deleteAll(paraArchivar);
        log.info("Artículos obsoletos archivados: {}", paraArchivar.size());
        return paraArchivar.size();
}
}
