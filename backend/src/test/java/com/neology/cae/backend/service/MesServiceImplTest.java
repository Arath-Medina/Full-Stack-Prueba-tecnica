package com.neology.cae.backend.service;

import com.neology.cae.backend.dao.EstanciaDao;
import com.neology.cae.backend.dao.ResidenteDao;
import com.neology.cae.backend.entity.Estancia;
import com.neology.cae.backend.entity.Residente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MesServiceImplTest {
    @Mock
    private EstanciaDao estanciaDao;

    @Mock
    private ResidenteDao residenteDao;

    @InjectMocks
    private MesServiceImpl mesService;

    @Test
    void eliminarEstanciasCompletadas() {
        // Given
        Estancia estanciaCompletada = Estancia.builder()
                .id(1L)
                .fechaHoraEntrada(LocalDateTime.of(2026, 9, 25, 8, 0))
                .fechaHoraSalida(LocalDateTime.of(2026, 9, 25, 10, 0))
                .minutos(120L)
                .build();

        when(estanciaDao.findByFechaHoraSalidaIsNotNull()).thenReturn(List.of(estanciaCompletada));
        when(residenteDao.findAll()).thenReturn(List.of());

        // When
        mesService.reiniciarMes();

        // Then
        verify(estanciaDao).deleteAll(List.of(estanciaCompletada));
    }

    @Test
    void reiniciarTiempoAcumuladoDeResidentes() {
        // Given
        Residente residente1 = Residente.builder()
                .id(1L)
                .tiempoAcumulado(210L)
                .build();

        Residente residente2 = Residente.builder()
                .id(2L)
                .tiempoAcumulado(75L)
                .build();

        when(estanciaDao.findByFechaHoraSalidaIsNotNull()).thenReturn(List.of());
        when(residenteDao.findAll()).thenReturn(List.of(residente1, residente2));

        // When
        mesService.reiniciarMes();

        // Then
        assertEquals(0L, residente1.getTiempoAcumulado());
        assertEquals(0L, residente2.getTiempoAcumulado());

        verify(residenteDao).saveAll(List.of(residente1, residente2));
    }

    @Test
    void noEliminarEstanciasActivas() {
        // Given
        when(estanciaDao.findByFechaHoraSalidaIsNotNull()).thenReturn(List.of());
        when(residenteDao.findAll()).thenReturn(List.of());

        // When
        mesService.reiniciarMes();

        // Then
        verify(estanciaDao, never()).deleteAll(anyList());
    }

}
