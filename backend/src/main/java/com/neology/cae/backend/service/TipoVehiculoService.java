package com.neology.cae.backend.service;

import com.neology.cae.backend.dto.TipoVehiculoRequest;
import com.neology.cae.backend.dto.TipoVehiculoResponse;
import com.neology.cae.backend.entity.TipoVehiculo;

import java.util.List;

public interface TipoVehiculoService {

    TipoVehiculoResponse crear(TipoVehiculoRequest request);

    List<TipoVehiculoResponse> listar();

    TipoVehiculo obtenerPorNombre(String nombre);
}