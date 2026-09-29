package com.neology.cae.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TipoVehiculoRequest(
        @NotBlank(message = "El nombre del tipo es obligatorio")
        String nombre,

        @NotNull(message = "La tarifa por minuto es obligatoria")
        @DecimalMin(value = "0.0", message = "La tarifa no puede ser negativa")
        BigDecimal tarifaPorMinuto
) {
}