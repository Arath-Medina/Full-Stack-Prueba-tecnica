import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Estancia, PagoResidente, TipoVehiculo, Vehiculo } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private http = inject(HttpClient);
  private base = '/neo';

  listarVehiculos(): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(`${this.base}/vehiculos`);
  }
  registrarVehiculo(placa: string, tipo: string): Observable<Vehiculo> {
    return this.http.post<Vehiculo>(`${this.base}/vehiculos`, { placa, tipo });
  }

  registrarVehiculoOficial(placa: string): Observable<Vehiculo> {
    return this.http.post<Vehiculo>(`${this.base}/vehiculos/oficiales`, { placa });
  }

  registrarVehiculoResidente(placa: string): Observable<Vehiculo> {
    return this.http.post<Vehiculo>(`${this.base}/vehiculos/residentes`, { placa });
  }

  registrarVehiculoNoResidente(placa: string): Observable<Vehiculo> {
    return this.http.post<Vehiculo>(`${this.base}/vehiculos/no-residentes`, { placa });
  }

  listarTiposDeVehiculo(): Observable<TipoVehiculo[]> {
    return this.http.get<TipoVehiculo[]>(`${this.base}/tipos-vehiculo`);
  }

  crearTipoDeVehiculo(nombre: string, tarifaPorMinuto: number): Observable<TipoVehiculo> {
    return this.http.post<TipoVehiculo>(`${this.base}/tipos-vehiculo`, { nombre, tarifaPorMinuto });
  }

  registrarEntrada(placa: string): Observable<Estancia> {
    return this.http.post<Estancia>(`${this.base}/estancias/entrada`, { placa });
  }

  registrarSalida(placa: string): Observable<Estancia> {
    return this.http.post<Estancia>(`${this.base}/estancias/salida`, { placa });
  }

  obtenerEstanciasPorPlaca(placa: string): Observable<Estancia[]> {
    return this.http.get<Estancia[]>(
      `${this.base}/estancias/vehiculo/${encodeURIComponent(placa)}`,
    );
  }

  obtenerReporteDePagos(): Observable<PagoResidente[]> {
    return this.http.get<PagoResidente[]>(`${this.base}/residentes/pagos`);
  }

  reiniciarEstancias(): Observable<void> {
    return this.http.post<void>(`${this.base}/mes/iniciar`, {});
  }

}
