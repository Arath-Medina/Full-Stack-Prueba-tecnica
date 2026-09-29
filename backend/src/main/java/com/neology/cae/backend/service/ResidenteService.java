package com.neology.cae.backend.service;

import com.neology.cae.backend.dto.PagoResidenteResponse;
import com.neology.cae.backend.entity.Residente;
import com.neology.cae.backend.entity.Vehiculo;

import java.util.List;

public interface ResidenteService {

    Residente registrar(Vehiculo vehiculo);

    void acumularMinutos(Vehiculo vehiculo, long minutos);

    List<PagoResidenteResponse> generarReportePagos();
}