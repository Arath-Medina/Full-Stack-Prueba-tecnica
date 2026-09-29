import { Injectable, inject } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({ providedIn: 'root' })
export class ErrorService {

  private snack = inject(MatSnackBar);

  show(error: unknown) {
    const e = error as HttpErrorResponse;
    const body = e?.error;
    const validation = body?.errores ? Object.values(body.errores).join(' · ') : '';
    this.snack.open(
      validation || body?.mensaje || 'Ocurrió un error al procesar la solicitud',
      'Cerrar',
      { duration: 5000 },
    );
  }

  success(message: string) {
    this.snack.open(message, 'Cerrar', { duration: 3000 });
  }

}
