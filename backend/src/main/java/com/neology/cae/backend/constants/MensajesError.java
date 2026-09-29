package com.neology.cae.backend.constants;

public final class MensajesError {

    private MensajesError() {
    }

    public static String vehiculoConEstanciaActiva(String placa) {
        return "El vehículo '" + placa + "' ya tiene una estancia activa";
    }

    public static String vehiculoSinEstanciaActiva(String placa) {
        return "El vehículo '" + placa + "' no tiene una estancia activa";
    }

    public static String vehiculoResidenteExistente(String placa) {
        return "El vehículo '" + placa + "' ya está registrado como residente";
    }

    public static String vehiculoResidenteNoExistente(String placa) {
        return "El vehículo '" + placa + "' no está registrado como residente";
    }

    public static String tipoVehiculoExistente(String nombre) {
        return "El tipo de vehículo '" + nombre + "' ya existe";
    }

    public static String vehiculoExistente(String placa) {
        return "El vehículo con placa '" + placa + "' ya existe";
    }

    public static String vehiculoNoExistente(String placa) {
        return "El vehículo con placa '" + placa + "' no existe";
    }

}
