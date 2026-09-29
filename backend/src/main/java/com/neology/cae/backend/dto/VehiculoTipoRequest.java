package com.neology.cae.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record VehiculoTipoRequest(
        @NotBlank(message = "La placa es obligatoria")
        String placa,

        @NotBlank(message = "El tipo de vehículo es obligatorio")
        String tipo
) {
}