package com.neology.cae.backend.dao;

import com.neology.cae.backend.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoDao extends JpaRepository<Vehiculo, String> {
}