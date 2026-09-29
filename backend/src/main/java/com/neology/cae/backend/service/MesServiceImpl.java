package com.neology.cae.backend.service;

import com.neology.cae.backend.dao.EstanciaDao;
import com.neology.cae.backend.dao.ResidenteDao;
import com.neology.cae.backend.entity.Estancia;
import com.neology.cae.backend.entity.Residente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MesServiceImpl implements MesService {
    private final EstanciaDao estanciaDao;
    private final ResidenteDao residenteDao;

    @Override
    public void reiniciarMes() {
        // Obtener estancias que ya fueron finalizadas
        List<Estancia> estanciasFinalizadas = estanciaDao.findByFechaHoraSalidaIsNotNull();

        // Eliminar estancias finalizadas si existen
        if (!estanciasFinalizadas.isEmpty()) {
            estanciaDao.deleteAll(estanciasFinalizadas);
        }

        // Reiniciar el tiempo acumulado de todos los residentes
        List<Residente> residentes = residenteDao.findAll();
        residentes.forEach(residente -> residente.setTiempoAcumulado(0L));

        // Actualizar registros
        residenteDao.saveAll(residentes);
    }
}
