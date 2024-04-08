import { Injectable } from '@angular/core';
import {Plan } from './plan';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class PlanesService {
  private planes: Plan [] = [
    {id: 3, idRutina: 3, reglaRecurrencia: "" , fechaInicio: new Date('2026-03-29T08:00:00'), fechaFin: new Date('2027-03-29T08:00:00')},
    {id: 2, idRutina: 2, reglaRecurrencia: "" , fechaInicio: new Date('2025-03-29T08:00:00'), fechaFin: new Date('2026-03-29T08:00:00')},
    {id: 1, idRutina: 1, reglaRecurrencia: "" ,fechaInicio: new Date('2024-03-29T08:00:00'), fechaFin: new Date('2025-03-29T08:00:00')}
  ];

  private planCambiadoSource = new Subject<void>();

  planCambiado$ = this.planCambiadoSource.asObservable();

  constructor() { }

  getPlanes(): Plan [] {
    return this.planes;
  }

  planCambiado() {
    this.planCambiadoSource.next();
  }
   
}
