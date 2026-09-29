import { Routes } from '@angular/router';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { VehiclesComponent } from './features/vehicles/vehicles.component';
import { VehicleDetailComponent } from './features/vehicles/vehicle-detail.component';
import { AccessComponent } from './features/access/access.component';
import { ResidentsComponent } from './features/residents/residents.component';
import { SettingsComponent } from './features/settings/settings.component';

export const routes: Routes = [

  { path: '', component: DashboardComponent },
  { path: 'vehiculos', component: VehiclesComponent },
  { path: 'vehiculos/:placa', component: VehicleDetailComponent },
  { path: 'accesos', component: AccessComponent },
  { path: 'residentes', component: ResidentsComponent },
  { path: 'configuracion', component: SettingsComponent },
  { path: '**', redirectTo: '' },

];
