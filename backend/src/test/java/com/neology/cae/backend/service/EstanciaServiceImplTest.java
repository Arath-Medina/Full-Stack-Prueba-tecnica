package com.neology.cae.backend.service;

import com.neology.cae.backend.constants.MensajesError;
import com.neology.cae.backend.dao.EstanciaDao;
import com.neology.cae.backend.dto.EstanciaRequest;
import com.neology.cae.backend.dto.EstanciaResponse;
import com.neology.cae.backend.entity.Estancia;
import com.neology.cae.backend.entity.TipoVehiculo;
import com.neology.cae.backend.entity.Vehiculo;
import com.neology.cae.backend.exception.OperacionInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstanciaServiceImplTest {
    @Mock
    private EstanciaDao estanciaDao;

    @Mock
    private VehiculoService vehiculoService;

    @Mock
    private ResidenteService residenteService;

    private Clock clock;

    private EstanciaServiceImpl estanciaService;

    private Vehiculo vehiculo;

    @BeforeEach
    void setUp() {
        // Hora fija para que los tests sean deterministas.
        clock = Clock.fixed(Instant.parse("2026-09-26T18:00:00Z"), ZoneId.of("UTC"));

        estanciaService = new EstanciaServiceImpl(estanciaDao, vehiculoService, residenteService, clock);

        TipoVehiculo tipo = TipoVehiculo.builder()
                .nombre("RESIDENTE")
                .tarifaPorMinuto(new BigDecimal("0.05"))
                .build();

        vehiculo = Vehiculo.builder()
                .placa("RES001")
                .tipo(tipo)
                .build();
    }

    @ParameterizedTest
    @CsvSource({
            "0,   0",
            "1,   1",
            "59,  1",
            "60,  1",
            "61,  2",
            "120, 2",
            "121, 3",
            "180, 3",
            "181, 4"
    })
    void registrarSalida(long segundosTranscurridos, long minutosEsperados) {
        String placa = "RES001";
        // Given

        // Mantener una hora de salida fija.
        LocalDateTime salida = LocalDateTime.now(clock);

        // Hora de entrada variable segun los casos de prueba
        LocalDateTime entrada = salida.minusSeconds(segundosTranscurridos);

        Estancia estancia = Estancia.builder()
                .id(1L)
                .vehiculo(vehiculo)
                .fechaHoraEntrada(entrada)
                .build();

        when(vehiculoService.obtenerPorPlaca(placa)).thenReturn(vehiculo);
        when(estanciaDao.findByVehiculoAndFechaHoraSalidaIsNull(vehiculo)).thenReturn(Optional.of(estancia));
        when(estanciaDao.save(estancia)).thenReturn(estancia);

        // When
        EstanciaResponse response = estanciaService.registrarSalida(new EstanciaRequest(placa));

        // Then
        assertNotNull(response.fechaHoraSalida());
        assertEquals(placa, response.placa());
        assertEquals(minutosEsperados, response.minutos());

        verify(estanciaDao).save(estancia);
    }

    @Test
    void rechazarRegistroDeSalida() {
        String placa = "RES001";

        // Given
        when(vehiculoService.obtenerPorPlaca(placa)).thenReturn(vehiculo);
        when(estanciaDao.findByVehiculoAndFechaHoraSalidaIsNull(vehiculo)).thenReturn(Optional.empty());

        // When & Then
        OperacionInvalidaException exception =
                assertThrows(
                        OperacionInvalidaException.class, () -> estanciaService.registrarSalida( new EstanciaRequest(placa))
                );

        assertEquals(MensajesError.vehiculoSinEstanciaActiva(placa), exception.getMessage());

        verify(estanciaDao, never()).save(any());
    }
}