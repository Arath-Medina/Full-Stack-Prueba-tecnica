package com.neology.cae.backend.controller;

import com.neology.cae.backend.dto.PagoResidenteResponse;
import com.neology.cae.backend.service.ResidenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/neo/residentes")
@RequiredArgsConstructor
public class ResidenteController {
    private final ResidenteService residenteService;

    @GetMapping("/pagos")
    public List<PagoResidenteResponse> generarReportePagos() {
        return residenteService.generarReportePagos();
    }
}