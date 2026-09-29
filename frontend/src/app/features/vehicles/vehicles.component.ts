import { AfterViewInit, Component, OnInit, ViewChild, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormsModule,
  ReactiveFormsModule,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { RouterLink } from '@angular/router';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ApiService } from '../../core/services/api.service';
import { ErrorService } from '../../core/services/error.service';
import { TipoVehiculo, Vehiculo } from '../../core/models/api.models';

@Component({
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './vehicles.component.html',
  styleUrl: './vehicles.component.scss',
})
export class VehiclesComponent implements OnInit, AfterViewInit {
  private apiService = inject(ApiService);
  private errorService = inject(ErrorService);
  tipos: TipoVehiculo[] = [];
  data = new MatTableDataSource<Vehiculo>([]);
  cols = ['placa', 'tipo', 'acciones'];
  saving = false;
  form = new FormGroup({
    placa: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    tipo: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngOnInit() {
    // Modificar el comportamiento por default del filtro para filtrar por placa.
    this.data.filterPredicate = (
      vehiculo: Vehiculo,
      placa: string,
    ): boolean => {
      return (
        vehiculo.placa.toLowerCase().includes(placa)
      );
    };

    this.load();
    this.apiService.listarTiposDeVehiculo().subscribe((x) => (this.tipos = x));
  }

  ngAfterViewInit() {
    this.data.paginator = this.paginator;
    this.data.sort = this.sort;
  }

  filter(e: Event) {
    this.data.filter = (e.target as HTMLInputElement).value.trim().toLowerCase();
  }

  load() {
    this.apiService.listarVehiculos().subscribe((x) => (this.data.data = x));
  }

  registrarVehiculo() {
    if (this.form.invalid) return;
    this.saving = true;
    const { placa, tipo } = this.form.getRawValue();
    this.apiService.registrarVehiculo(placa, tipo).subscribe({
      next: () => {
        this.errorService.success('Vehículo registrado');
        this.form.reset();
        this.load();
        this.saving = false;
      },
      error: (e) => {
        this.errorService.show(e);
        this.saving = false;
      },
    });
  }

}
