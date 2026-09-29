import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatDialogModule } from '@angular/material/dialog';
import { ApiService } from '../../core/services/api.service';
import { ErrorService } from '../../core/services/error.service';
import { TipoVehiculo } from '../../core/models/api.models';
@Component({
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatDialogModule,
  ],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
})
export class SettingsComponent implements OnInit {
  private apiService = inject(ApiService);
  private errorService = inject(ErrorService);
  tipos: TipoVehiculo[] = [];
  form = new FormGroup({
    nombre: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    tarifa: new FormControl(0, {
      nonNullable: true,
      validators: [Validators.required, Validators.min(0)],
    }),
  });

  ngOnInit() {
    this.load();
  }

  /**
   * Mostrar catalogo de tipos de vehiculo.
   */
  load() {
    this.apiService.listarTiposDeVehiculo().subscribe((x) => (this.tipos = x));
  }

  crearTipoVehiculo() {
    if (this.form.invalid) return;
    const v = this.form.getRawValue();
    this.apiService.crearTipoDeVehiculo(v.nombre, v.tarifa).subscribe({
      next: () => {
        this.errorService.success('Tipo creado');
        this.form.reset({ nombre: '', tarifa: 0 });
        this.load();
      },
      error: (e) => this.errorService.show(e),
    });
  }

  reiniciarEstancias() {
    if (
      !confirm(
        '¿Reiniciar el mes? Esta acción eliminará las estancias completadas y pondrá en cero los minutos de residentes.'
      )
    )
      return;
    this.apiService
      .reiniciarEstancias()
      .subscribe({
        next: () => this.errorService.success('Mes reiniciado correctamente'),
        error: (e) => this.errorService.show(e),
      });
  }

}
