package com.neology.cae.backend.dao;

import com.neology.cae.backend.entity.Estancia;
import com.neology.cae.backend.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstanciaDao extends JpaRepository<Estancia, Long> {

    Optional<Estancia> findByVehiculoAndFechaHoraSalidaIsNull(Vehiculo vehiculo);

    List<Estancia> findByFechaHoraSalidaIsNotNull();

    List<Estancia> findByVehiculoOrderByFechaHoraEntradaDesc(Vehiculo vehiculo);
}