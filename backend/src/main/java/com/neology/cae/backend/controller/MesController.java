package com.neology.cae.backend.controller;

import com.neology.cae.backend.service.MesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/neo/mes")
@RequiredArgsConstructor
public class MesController {
    private final MesService mesService;

    @PostMapping("/iniciar")
    public ResponseEntity<Void> reiniciarMes() {
        mesService.reiniciarMes();
        return ResponseEntity.noContent().build();
    }
}