package com.neology.cae.backend.service;

import com.neology.cae.backend.constants.MensajesError;
import com.neology.cae.backend.dto.VehiculoRequest;
import com.neology.cae.backend.dto.VehiculoResponse;
import com.neology.cae.backend.dto.VehiculoTipoRequest;
import com.neology.cae.backend.entity.TipoVehiculo;
import com.neology.cae.backend.entity.Vehiculo;
import com.neology.cae.backend.exception.RecursoDuplicadoException;
import com.neology.cae.backend.exception.RecursoNoEncontradoException;
import com.neology.cae.backend.util.Normalizador;
import com.neology.cae.backend.constants.TiposVehiculo;
import com.neology.cae.backend.dao.VehiculoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculoServiceImpl implements VehiculoService {
    private final VehiculoDao vehiculoDao;
    private final TipoVehiculoService tipoVehiculoService;
    private final ResidenteService residenteService;

    @Override
    public VehiculoResponse registrarOficial(VehiculoRequest request) {
        Vehiculo vehiculo = registrarVehiculo(request.placa(), TiposVehiculo.OFICIAL);
        return convertirAResponse(vehiculo);
    }

    @Override
    public VehiculoResponse registrarResidente(VehiculoRequest request) {
        Vehiculo vehiculo = registrarVehiculo(request.placa(), TiposVehiculo.RESIDENTE);
        return convertirAResponse(vehiculo);
    }

    @Override
    public VehiculoResponse registrarNoResidente(VehiculoRequest request) {
        Vehiculo vehiculo = registrarVehiculo(request.placa(), TiposVehiculo.NO_RESIDENTE);
        return convertirAResponse(vehiculo);
    }

    @Override
    public VehiculoResponse registrar(VehiculoTipoRequest request) {
        Vehiculo vehiculo = registrarVehiculo(request.placa(), request.tipo());
        return convertirAResponse(vehiculo);
    }

    @Override
    public List<VehiculoResponse> listar() {
        return vehiculoDao.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public Vehiculo obtenerPorPlaca(String placa) {
        String placaNormalizada = Normalizador.normalizarPlaca(placa);

        return vehiculoDao.findById(placaNormalizada)
                .orElseThrow(() -> new RecursoNoEncontradoException(MensajesError.vehiculoNoExistente(placaNormalizada)));
    }

    /**
     * Realiza el proceso completo de registro de vehiculos + residente si es que aplica.
     * @param placa Placa del vehiculo.
     * @param nombreTipo Tipo de vehiculo.
     * @return Objeto Vehiculo creado.
     */
    private Vehiculo registrarVehiculo(String placa, String nombreTipo) {
        // Crear vehiculo
        Vehiculo vehiculo = crearVehiculo(placa, nombreTipo);

        // Registrar residente si el tipo coincide
        if (TiposVehiculo.RESIDENTE.equals(vehiculo.getTipo().getNombre())) {
            residenteService.registrar(vehiculo);
        }

        return vehiculo;
    }

    /**
     * Metodo para el registro de vehiculos.
     * @param placa Placa del vehiculo.
     * @param nombreTipo Tipo de vehiculo.
     * @return Objeto Vehiculo correspondiente al registro creado.
     */
    private Vehiculo crearVehiculo(String placa, String nombreTipo) {
        String placaNormalizada = Normalizador.normalizarPlaca(placa);
        String tipoNormalizado = Normalizador.normalizarTipoVehiculo(nombreTipo);

        // Validar si ya existe el registro
        if (vehiculoDao.existsById(placaNormalizada)) {
            throw new RecursoDuplicadoException(MensajesError.vehiculoExistente(placaNormalizada));
        }

        TipoVehiculo tipo = tipoVehiculoService.obtenerPorNombre(tipoNormalizado);

        Vehiculo vehiculo = Vehiculo.builder()
                .placa(placaNormalizada)
                .tipo(tipo)
                .build();

        // Guardar el registro
        return vehiculoDao.save(vehiculo);
    }

    /**
     * Crear response a partir de la entidad.
     * @param vehiculo Entidad para crear la respuesta.
     * @return Objeto Response.
     */
    private VehiculoResponse convertirAResponse(Vehiculo vehiculo) {
        return new VehiculoResponse(vehiculo.getPlaca(), vehiculo.getTipo().getNombre());
    }

}