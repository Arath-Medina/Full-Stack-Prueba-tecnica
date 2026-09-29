package com.neology.cae.backend.util;

public final class Normalizador {
    private Normalizador() {
    }

    /**
     * Normalizar placa a mayusculas.
     * @param placa PLaca a normalizar.
     * @return Placa normalizada.
     */
    public static String normalizarPlaca(String placa) {
        return placa.trim().toUpperCase();
    }

    /**
     * Normalizar tipo vehiculo a mayusculas.
     * @param nombre Nombre del tipo de vehiculo a normalizar.
     * @return Nombre normalizado.
     */
    public static String normalizarTipoVehiculo(String nombre) {
        return nombre.trim().toUpperCase();
    }

}
