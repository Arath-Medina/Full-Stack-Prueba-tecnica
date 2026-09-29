package com.neology.cae.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record EstanciaRequest(
        @NotBlank(message = "La placa es obligatoria")
        String placa
) {
}
