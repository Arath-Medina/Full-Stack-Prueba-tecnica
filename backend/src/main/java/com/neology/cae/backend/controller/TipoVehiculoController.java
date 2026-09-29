package com.neology.cae.backend.controller;

import com.neology.cae.backend.dto.TipoVehiculoRequest;
import com.neology.cae.backend.dto.TipoVehiculoResponse;
import com.neology.cae.backend.service.TipoVehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/neo/tipos-vehiculo")
@RequiredArgsConstructor
public class TipoVehiculoController {
    private final TipoVehiculoService tipoVehiculoService;

    @PostMapping
    public ResponseEntity<TipoVehiculoResponse> crear(@Valid @RequestBody TipoVehiculoRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tipoVehiculoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<TipoVehiculoResponse>> listar() {
        return ResponseEntity.ok(tipoVehiculoService.listar()
        );
    }
}