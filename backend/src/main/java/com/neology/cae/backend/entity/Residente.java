package com.neology.cae.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "residentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Residente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "placa", nullable = false, unique = true)
    private Vehiculo vehiculo;

    @Column(nullable = false)
    private Long tiempoAcumulado;
}
