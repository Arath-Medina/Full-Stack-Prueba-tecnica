package com.neology.cae.backend.dto;

import java.math.BigDecimal;

public record PagoResidenteResponse(
        String placa,
        Long minutosAcumulados,
        BigDecimal tarifaPorMinuto,
        BigDecimal total
) {
}