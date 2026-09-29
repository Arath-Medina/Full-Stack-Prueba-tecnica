package com.neology.cae.backend.dao;

import com.neology.cae.backend.entity.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoVehiculoDao extends JpaRepository<TipoVehiculo, String> {
}