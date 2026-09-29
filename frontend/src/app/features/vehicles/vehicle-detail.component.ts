import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { forkJoin } from 'rxjs';
import { ApiService } from '../../core/services/api.service';
import { Estancia, PagoResidente, Vehiculo } from '../../core/models/api.models';

@Component({
  standalone: true,
  imports: [CommonModule, RouterLink, MatButtonModule, MatIconModule, MatTableModule],
  templateUrl: './vehicle-detail.component.html',
  styleUrl: './vehicle-detail.component.scss',
})
export class VehicleDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private apiService = inject(ApiService);
  placa = '';
  vehiculo?: Vehiculo;
  pago?: PagoResidente;
  estancias: Estancia[] = [];
  cols = ['entrada', 'salida', 'minutos'];

  ngOnInit() {
    this.placa = this.route.snapshot.paramMap.get('placa') ?? '';
    forkJoin({
      v: this.apiService.listarVehiculos(),
      e: this.apiService.obtenerEstanciasPorPlaca(this.placa),
      p: this.apiService.obtenerReporteDePagos(),
    }).subscribe(({ v, e, p }) => {
      this.vehiculo = v.find((x) => x.placa === this.placa);
      this.estancias = e;
      this.pago = p.find((x) => x.placa === this.placa);
    });
  }

}
