package com.neology.cae.backend.service;

import com.neology.cae.backend.constants.MensajesError;
import com.neology.cae.backend.dao.ResidenteDao;
import com.neology.cae.backend.dto.PagoResidenteResponse;
import com.neology.cae.backend.entity.Residente;
import com.neology.cae.backend.entity.Vehiculo;
import com.neology.cae.backend.exception.RecursoDuplicadoException;
import com.neology.cae.backend.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResidenteServiceImpl implements ResidenteService {
    private final ResidenteDao residenteDao;

    @Override
    public Residente registrar(Vehiculo vehiculo) {
        // Validar si ya existe el registro
        if (residenteDao.findByVehiculo(vehiculo).isPresent()) {
            throw new RecursoDuplicadoException(MensajesError.vehiculoResidenteExistente(vehiculo.getPlaca()));
        }

        Residente residente = Residente.builder()
                .vehiculo(vehiculo)
                .tiempoAcumulado(0L)
                .build();

        // Guardar el registro
        return residenteDao.save(residente);
    }

    @Override
    public void acumularMinutos(Vehiculo vehiculo, long minutos) {
        // Validar si el vehiculo corresponde a un residente
        Residente residente = residenteDao
                .findByVehiculo(vehiculo)
                .orElseThrow(() -> new RecursoNoEncontradoException(MensajesError.vehiculoResidenteNoExistente(vehiculo.getPlaca())));

        residente.setTiempoAcumulado(residente.getTiempoAcumulado() + minutos);

        // Guardar registro
        residenteDao.save(residente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResidenteResponse> generarReportePagos() {
        return residenteDao.findAll()
                .stream()
                .map(this::convertirAPagoResponse)
                .toList();
    }

    /**
     * Crear response a partir de la entidad.
     * @param residente Entidad para crear la respuesta.
     * @return Objeto Response.
     */
    private PagoResidenteResponse convertirAPagoResponse(Residente residente) {
        BigDecimal tarifa = residente.getVehiculo()
                .getTipo()
                .getTarifaPorMinuto();

        // Total = tarifa * tiempoAcumulado
        BigDecimal total = tarifa.multiply(BigDecimal.valueOf(residente.getTiempoAcumulado()));

        return new PagoResidenteResponse(
                residente.getVehiculo().getPlaca(),
                residente.getTiempoAcumulado(),
                tarifa,
                total
        );
    }
}