package com.neology.cae.backend.controller;

import com.neology.cae.backend.dto.VehiculoRequest;
import com.neology.cae.backend.dto.VehiculoResponse;
import com.neology.cae.backend.dto.VehiculoTipoRequest;
import com.neology.cae.backend.service.VehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/neo/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {
    private final VehiculoService vehiculoService;

    @PostMapping("/oficiales")
    public ResponseEntity<VehiculoResponse> registrarOficial(@Valid @RequestBody VehiculoRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehiculoService.registrarOficial(request));
    }

    @PostMapping("/residentes")
    public ResponseEntity<VehiculoResponse> registrarResidente(@Valid @RequestBody VehiculoRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehiculoService.registrarResidente(request));
    }

    @PostMapping("/no-residentes")
    public ResponseEntity<VehiculoResponse> registrarNoResidente(@Valid @RequestBody VehiculoRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehiculoService.registrarNoResidente(request));
    }

    @PostMapping
    public ResponseEntity<VehiculoResponse> registrar(@Valid @RequestBody VehiculoTipoRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehiculoService.registrar(request));
    }

    @GetMapping
    public ResponseEntity<List<VehiculoResponse>> listar() {
        return ResponseEntity.ok(vehiculoService.listar());
    }
}