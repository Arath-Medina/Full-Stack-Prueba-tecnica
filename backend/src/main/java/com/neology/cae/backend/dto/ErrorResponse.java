package com.neology.cae.backend.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        int status,
        String mensaje,
        LocalDateTime timestamp,
        Map<String, String> errores
) {
}