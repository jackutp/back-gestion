package com.microservicio.incidentes.controller;

import com.microservicio.incidentes.dto.AlertManagerRequest;
import com.microservicio.incidentes.service.AlertManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incidentes")
@RequiredArgsConstructor
public class AlertManagerController {
    private final AlertManagerService alertManagerService;

    @PostMapping
    public ResponseEntity<Void> receiveAlert( @RequestBody AlertManagerRequest request) {
        alertManagerService.process(request);
        return ResponseEntity.ok().build();
    }
}
