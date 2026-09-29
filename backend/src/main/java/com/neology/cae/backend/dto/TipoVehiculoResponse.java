package com.neology.cae.backend.dto;

import java.math.BigDecimal;

public record TipoVehiculoResponse(
        String nombre,
        BigDecimal tarifaPorMinuto
) {
}