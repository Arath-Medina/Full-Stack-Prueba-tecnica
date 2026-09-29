export interface Vehiculo {
  placa: string;
  tipo: string;
}

export interface TipoVehiculo {
  nombre: string;
  tarifaPorMinuto: number;
}

export interface Estancia {
  placa: string;
  fechaHoraEntrada: string;
  fechaHoraSalida: string | null;
  minutos: number | null;
  montoPagar: number | null;
}

export interface PagoResidente {
  placa: string;
  minutosAcumulados: number;
  tarifaPorMinuto: number;
  total: number;
}

export interface ApiError {
  status: number;
  mensaje: string;
  timestamp: string;
  errores?: Record<string, string>;
}
