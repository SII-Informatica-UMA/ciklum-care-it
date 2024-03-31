import { Injectable } from '@angular/core';
import {Plan } from './plan';

@Injectable({
  providedIn: 'root'
})

export class PlanesService {
  private planes: Plan [] = [
    {id: 1, idRutina: 1, reglaRecurrencia: "" ,fechaInicio: "2024-03-28T15:27:53.893Z", fechaFin: "2024-03-28T15:27:53.893Z"},
    {id: 2, idRutina: 2, reglaRecurrencia: "" , fechaInicio: "2024-04-20T15:27:53.893Z", fechaFin: "2024-05-28T15:27:53.893Z"},
    {id: 3, idRutina: 3, reglaRecurrencia: "" , fechaInicio: "2024-06-28T15:27:53.893Z", fechaFin: "2024-07-28T15:27:53.893Z"},
  ];

  constructor() { }

  getPlanes(): Plan [] {
    return this.planes;
  }
   
}
