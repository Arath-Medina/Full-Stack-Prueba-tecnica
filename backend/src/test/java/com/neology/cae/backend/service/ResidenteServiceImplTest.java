package com.neology.cae.backend.service;

import com.neology.cae.backend.constants.MensajesError;
import com.neology.cae.backend.dao.ResidenteDao;
import com.neology.cae.backend.dto.PagoResidenteResponse;
import com.neology.cae.backend.entity.Residente;
import com.neology.cae.backend.entity.TipoVehiculo;
import com.neology.cae.backend.entity.Vehiculo;
import com.neology.cae.backend.exception.RecursoDuplicadoException;
import com.neology.cae.backend.exception.RecursoNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResidenteServiceImplTest {
    @Mock
    private ResidenteDao residenteDao;

    @InjectMocks
    private ResidenteServiceImpl residenteService;

    private Vehiculo vehiculo;

    @BeforeEach
    void setUp() {
        TipoVehiculo tipo = TipoVehiculo.builder()
                .nombre("RESIDENTE")
                .tarifaPorMinuto(new BigDecimal("0.05"))
                .build();

        vehiculo = Vehiculo.builder()
                .placa("RES001")
                .tipo(tipo)
                .build();
    }

    @Test
    void registrarResidente() {
        // Given
        when(residenteDao.findByVehiculo(vehiculo)).thenReturn(Optional.empty());

        Residente residenteGuardado = Residente.builder()
                .id(1L)
                .vehiculo(vehiculo)
                .tiempoAcumulado(0L)
                .build();

        when(residenteDao.save(any(Residente.class))).thenReturn(residenteGuardado);

        // When
        Residente resultado = residenteService.registrar(vehiculo);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(vehiculo, resultado.getVehiculo());
        assertEquals(0L, resultado.getTiempoAcumulado());

        verify(residenteDao).save(any(Residente.class));
    }

    @Test
    void registrarResidente_ErrorResidenteYaExiste() {
        // Given
        Residente residenteExistente = Residente.builder()
                .id(1L)
                .vehiculo(vehiculo)
                .tiempoAcumulado(120L)
                .build();

        when(residenteDao.findByVehiculo(vehiculo)).thenReturn(Optional.of(residenteExistente));

        // When & Then
        RecursoDuplicadoException exception = assertThrows(RecursoDuplicadoException.class, () -> residenteService.registrar(vehiculo));

        assertEquals(MensajesError.vehiculoResidenteExistente(vehiculo.getPlaca()), exception.getMessage());

        verify(residenteDao, never()).save(any());
    }

    @Test
    void acumularMinutos() {
        // Given
        Residente residente = Residente.builder()
                .id(1L)
                .vehiculo(vehiculo)
                .tiempoAcumulado(120L)
                .build();

        when(residenteDao.findByVehiculo(vehiculo)).thenReturn(Optional.of(residente));

        // When
        residenteService.acumularMinutos(vehiculo, 45L);

        // Then
        assertEquals(165L, residente.getTiempoAcumulado());

        verify(residenteDao).save(residente);
    }

    @Test
    void acumularMinutos_ErrorVehiculoNoEsResidente() {
        // Given
        when(residenteDao.findByVehiculo(vehiculo)).thenReturn(Optional.empty());

        // When & Then
        RecursoNoEncontradoException exception =
                assertThrows(RecursoNoEncontradoException.class, () -> residenteService.acumularMinutos(vehiculo, 45L));

        assertEquals(MensajesError.vehiculoResidenteNoExistente(vehiculo.getPlaca()), exception.getMessage());

        verify(residenteDao, never()).save(any());
    }

    @Test
    void debeGenerarReporteDePagos() {
        // Given
        Residente residente = Residente.builder()
                .id(1L)
                .vehiculo(vehiculo)
                .tiempoAcumulado(241L)
                .build();

        when(residenteDao.findAll()).thenReturn(List.of(residente));

        // When
        List<PagoResidenteResponse> reporte = residenteService.generarReportePagos();

        // Then
        assertEquals(1, reporte.size());

        PagoResidenteResponse pago = reporte.get(0);

        assertEquals("RES001", pago.placa());
        assertEquals(241L, pago.minutosAcumulados());
        assertEquals(new BigDecimal("0.05"), pago.tarifaPorMinuto());
        assertEquals(new BigDecimal("12.05"), pago.total());
    }

}