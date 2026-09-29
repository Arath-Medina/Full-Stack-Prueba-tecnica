import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ApiService } from '../../core/services/api.service';
import { ErrorService } from '../../core/services/error.service';
import { Estancia } from '../../core/models/api.models';

@Component({
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './access.component.html',
  styleUrl: './access.component.scss',
})
export class AccessComponent {
  private apiService = inject(ApiService);
  private errorService = inject(ErrorService);
  placa = new FormControl('', { nonNullable: true, validators: [Validators.required] });
  loading = false;
  last?: Estancia;

  registrarEntrada() {
    this.run('entrada');
  }

  registrarSalida() {
    this.run('salida');
  }

  private run(type: 'entrada' | 'salida'): void {
    if (this.placa.invalid) {
      this.placa.markAsTouched();
      return;
    }

    this.loading = true;

    const request =
      type === 'entrada'
        ? this.apiService.registrarEntrada(this.placa.value)
        : this.apiService.registrarSalida(this.placa.value);

    request.subscribe({
      next: (response: Estancia) => {
        this.last = response;
        this.loading = false;

        this.errorService.success(
          type === 'entrada'
            ? 'Entrada registrada correctamente'
            : 'Salida registrada correctamente',
        );
      },
      error: (error: unknown) => {
        this.loading = false;
        this.errorService.show(error);
      },
    });
  }

}
