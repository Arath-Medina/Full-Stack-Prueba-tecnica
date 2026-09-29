package com.neology.cae.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EstanciaResponse(
        String placa,
        LocalDateTime fechaHoraEntrada,
        LocalDateTime fechaHoraSalida,
        Long minutos,
        BigDecimal montoPagar
) {
}
