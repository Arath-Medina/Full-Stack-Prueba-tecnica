package com.neology.cae.backend.service;

import com.neology.cae.backend.dto.VehiculoRequest;
import com.neology.cae.backend.dto.VehiculoResponse;
import com.neology.cae.backend.dto.VehiculoTipoRequest;
import com.neology.cae.backend.entity.Vehiculo;

import java.util.List;

public interface VehiculoService {

    VehiculoResponse registrarOficial(VehiculoRequest request);

    VehiculoResponse registrarResidente(VehiculoRequest request);

    VehiculoResponse registrarNoResidente(VehiculoRequest request);

    VehiculoResponse registrar(VehiculoTipoRequest request);

    List<VehiculoResponse> listar();

    Vehiculo obtenerPorPlaca(String placa);
}