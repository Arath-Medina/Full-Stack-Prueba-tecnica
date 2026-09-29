package com.neology.cae.backend.dao;

import com.neology.cae.backend.entity.Residente;
import com.neology.cae.backend.entity.Vehiculo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResidenteDao extends JpaRepository<Residente, Long> {

    Optional<Residente> findByVehiculo(Vehiculo vehiculo);

    @Override
    @EntityGraph(attributePaths = {"vehiculo", "vehiculo.tipo"})
    List<Residente> findAll();
}