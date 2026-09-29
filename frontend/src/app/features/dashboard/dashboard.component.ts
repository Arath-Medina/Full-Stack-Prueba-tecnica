import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { forkJoin } from 'rxjs';
import { ApiService } from '../../core/services/api.service';

@Component({
  standalone: true,
  imports: [CommonModule, RouterLink, MatButtonModule, MatIconModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  private apiService = inject(ApiService);
  vehicles = 0;
  residents = 0;
  types = 0;
  total = 0;

  ngOnInit() {
    forkJoin({
      v: this.apiService.listarVehiculos(),
      p: this.apiService.obtenerReporteDePagos(),
      t: this.apiService.listarTiposDeVehiculo(),
    }).subscribe(({ v, p, t }) => {
      this.vehicles = v.length;
      this.residents = p.length;
      this.types = t.length;
      this.total = p.reduce((s, x) => s + Number(x.total), 0);
    });
  }

}
