package com.neology.cae.backend.service;

import com.neology.cae.backend.constants.MensajesError;
import com.neology.cae.backend.constants.TiposVehiculo;
import com.neology.cae.backend.dao.EstanciaDao;
import com.neology.cae.backend.dto.EstanciaRequest;
import com.neology.cae.backend.dto.EstanciaResponse;
import com.neology.cae.backend.entity.Estancia;
import com.neology.cae.backend.entity.Vehiculo;
import com.neology.cae.backend.exception.OperacionInvalidaException;
import com.neology.cae.backend.util.Normalizador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstanciaServiceImpl implements EstanciaService {
    private final EstanciaDao estanciaDao;
    private final VehiculoService vehiculoService;
    private final ResidenteService residenteService;
    private final Clock clock;

    @Override
    public EstanciaResponse registrarEntrada(EstanciaRequest request) {
        String placa = Normalizador.normalizarPlaca(request.placa());

        // Validar si existe el vehiculo
        Vehiculo vehiculo = vehiculoService.obtenerPorPlaca(placa);

        // Validar si ya hay una estancia en curso
        estanciaDao.findByVehiculoAndFechaHoraSalidaIsNull(vehiculo)
                .ifPresent(estancia -> {
                    throw new OperacionInvalidaException(MensajesError.vehiculoConEstanciaActiva(placa));
                });

        Estancia estancia = Estancia.builder()
                .vehiculo(vehiculo)
                .fechaHoraEntrada(LocalDateTime.now(clock))
                .build();

        // Registrar estancia
        Estancia guardada = estanciaDao.save(estancia);

        return convertirAResponse(guardada, null);
    }

    @Override
    public EstanciaResponse registrarSalida(EstanciaRequest request) {
        String placa = Normalizador.normalizarPlaca(request.placa());

        // Valida si existe el vehiculo
        Vehiculo vehiculo = vehiculoService.obtenerPorPlaca(placa);

        // Validar si existe una estancia activa
        Estancia estancia = estanciaDao.findByVehiculoAndFechaHoraSalidaIsNull(vehiculo)
                        .orElseThrow(() -> new OperacionInvalidaException(MensajesError.vehiculoSinEstanciaActiva(placa)));

        LocalDateTime salida = LocalDateTime.now(clock);

        // Calcular minutos transcurridos
        long minutos = calcularMinutos(estancia.getFechaHoraEntrada(), salida);

        estancia.setFechaHoraSalida(salida);
        estancia.setMinutos(minutos);

        BigDecimal montoPagar = calcularPagoEstancia(vehiculo, minutos);

        if (TiposVehiculo.RESIDENTE.equals(Normalizador.normalizarTipoVehiculo(vehiculo.getTipo().getNombre()))) {
            residenteService.acumularMinutos(vehiculo, minutos);
            montoPagar = null;
        }

        // Registrar salida
        Estancia actualizada = estanciaDao.save(estancia);

        return convertirAResponse(actualizada, montoPagar);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstanciaResponse> listarPorPlaca(String placa) {
        String placaNormalizada = Normalizador.normalizarPlaca(placa);
        Vehiculo vehiculo = vehiculoService.obtenerPorPlaca(placaNormalizada);

        return estanciaDao.findByVehiculoOrderByFechaHoraEntradaDesc(vehiculo)
                .stream()
                .map(estancia -> convertirAResponse(estancia, null))
                .toList();
    }

    /**
     * Calcular el monto a pagar dada la estancia del vehiculo en el estacionamiento.
     * @param vehiculo Vehiculo en el estacionamiento.
     * @param minutos Minutos transcurridos en el estacinamiento.
     * @return MOnto a pagar.
     */
    private BigDecimal calcularPagoEstancia(Vehiculo vehiculo, long minutos) {
        return vehiculo.getTipo()
                .getTarifaPorMinuto()
                .multiply(BigDecimal.valueOf(minutos));
    }

    /**
     * Calcular tiempo transcurrido entre una fecha de entrada
     * y una de entrada y una de salida.
     * El calculo contempla un redondeo hacia arriba de los segundos en proceso.
     * @param entrada Fecha y hora de entrada.
     * @param salida Fecha y hora de salida.
     * @return minutos transcurridos.
     */
    private long calcularMinutos(LocalDateTime entrada, LocalDateTime salida) {
        // Calcular los segundos transcurridos.
        long segundos = Duration
                .between(entrada, salida)
                .getSeconds();
        // Redondear hacia arriba los minutos en curso.
        return (segundos + 59) / 60;
    }

    /**
     * Crear response a partir de la entidad.
     * @param estancia Entidad para crear la respuesta.
     * @return Objeto Response.
     */
    private EstanciaResponse convertirAResponse(Estancia estancia, BigDecimal montoPagar) {
        return new EstanciaResponse(
                estancia.getVehiculo().getPlaca(),
                estancia.getFechaHoraEntrada(),
                estancia.getFechaHoraSalida(),
                estancia.getMinutos(),
                montoPagar
        );
    }

}