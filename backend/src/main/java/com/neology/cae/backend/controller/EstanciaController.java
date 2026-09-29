package com.neology.cae.backend.controller;

import com.neology.cae.backend.dto.EstanciaRequest;
import com.neology.cae.backend.dto.EstanciaResponse;
import com.neology.cae.backend.service.EstanciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/neo/estancias")
@RequiredArgsConstructor
public class EstanciaController {
    private final EstanciaService estanciaService;

    @PostMapping("/entrada")
    public ResponseEntity<EstanciaResponse> registrarEntrada(@Valid @RequestBody EstanciaRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estanciaService.registrarEntrada(request));
    }

    @PostMapping("/salida")
    public ResponseEntity<EstanciaResponse> registrarSalida(@Valid @RequestBody EstanciaRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estanciaService.registrarSalida(request));
    }

    @GetMapping("/vehiculo/{placa}")
    public ResponseEntity<List<EstanciaResponse>> listarPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(estanciaService.listarPorPlaca(placa));
    }
}