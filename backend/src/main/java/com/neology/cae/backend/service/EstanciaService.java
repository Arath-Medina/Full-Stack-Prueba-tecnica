package com.neology.cae.backend.service;

import com.neology.cae.backend.dto.EstanciaRequest;
import com.neology.cae.backend.dto.EstanciaResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface EstanciaService {

    EstanciaResponse registrarEntrada(EstanciaRequest request);

    EstanciaResponse registrarSalida(EstanciaRequest request);

    List<EstanciaResponse> listarPorPlaca(String placa);

}
