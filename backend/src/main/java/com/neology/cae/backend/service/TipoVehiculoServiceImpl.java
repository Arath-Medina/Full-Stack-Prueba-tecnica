package com.neology.cae.backend.service;

import com.neology.cae.backend.constants.MensajesError;
import com.neology.cae.backend.dao.TipoVehiculoDao;
import com.neology.cae.backend.dto.TipoVehiculoRequest;
import com.neology.cae.backend.dto.TipoVehiculoResponse;
import com.neology.cae.backend.entity.TipoVehiculo;
import com.neology.cae.backend.exception.RecursoDuplicadoException;
import com.neology.cae.backend.exception.RecursoNoEncontradoException;
import com.neology.cae.backend.util.Normalizador;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoVehiculoServiceImpl implements TipoVehiculoService {

    private final TipoVehiculoDao tipoVehiculoDao;

    @Override
    public TipoVehiculoResponse crear(TipoVehiculoRequest request) {
        String nombre = Normalizador.normalizarTipoVehiculo(request.nombre());

        // Validar si ya existe el registro
        if (tipoVehiculoDao.existsById(nombre)) {
            throw new RecursoDuplicadoException(MensajesError.tipoVehiculoExistente(nombre));
        }

        TipoVehiculo tipoVehiculo = TipoVehiculo.builder()
                .nombre(nombre)
                .tarifaPorMinuto(request.tarifaPorMinuto())
                .build();

        // Guardar registro
        TipoVehiculo guardado = tipoVehiculoDao.save(tipoVehiculo);

        return convertirAResponse(guardado);
    }

    @Override
    public List<TipoVehiculoResponse> listar() {
        return tipoVehiculoDao.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public TipoVehiculo obtenerPorNombre(String nombre) {
        String nombreNormalizado = nombre.trim().toUpperCase();

        return tipoVehiculoDao.findById(nombreNormalizado)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(MensajesError.tipoVehiculoExistente(nombreNormalizado))
                );
    }

    private TipoVehiculoResponse convertirAResponse(TipoVehiculo tipoVehiculo) {
        return new TipoVehiculoResponse(tipoVehiculo.getNombre(), tipoVehiculo.getTarifaPorMinuto());
    }
}
