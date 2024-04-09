import { Injectable } from '@angular/core';
import {Sesion } from './sesion';

@Injectable({
  providedIn: 'root'
})
export class SesionesService {
  private sesiones: Sesion [] = [
    {idPlan: 2, inicio: '3000-10-29T08:00:00', fin: '2024-10-29T09:00:00', 
	trabajoRealizado: 'Trabajo realizado', multimedia: [],
	descripcion: 'Descripción4', presencial: true, datosSalud: [], id: 4},
    {idPlan: 1, inicio: '2024-03-29T08:00:00', fin: '2024-03-29T09:00:00', 
	trabajoRealizado: 'Trabajo realizado', multimedia: [],
	descripcion: 'Descripción1', presencial: true, datosSalud: [], id: 1},
    {idPlan: 1, inicio: '2024-03-30T08:00:00', fin: '2024-03-30T09:00:00', 
	trabajoRealizado: 'Trabajo realizado', multimedia: [],
	descripcion: 'Descripción2', presencial: false, datosSalud: [], id: 2},
    {idPlan: 2, inicio: '3000-03-29T08:00:00', fin: '2024-03-29T09:00:00', 
	trabajoRealizado: 'Trabajo realizado', multimedia: [],
	descripcion: 'Descripción3', presencial: true, datosSalud: [], id: 3},
  {idPlan: 2, inicio: '3000-03-29T08:00:00', fin: '2024-05-29T09:00:00', 
	trabajoRealizado: 'AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH', multimedia: [],
	descripcion: 'Descripción5. La verdad es que no me ha gustado nada este entrenamiento. Me gustaria que dejasen de poner ese tipo de cosas porque me acaba doliendo la espalda.', presencial: true, datosSalud: [], id: 5},
  ];

  constructor() { }

  getSesiones(idPlan: number): Sesion [] {
    return this.sesiones.filter(sesion => sesion.idPlan === idPlan);
  }

  addSesion(sesion: Sesion) {
    sesion.id = Math.max(...this.sesiones.map(c => c.id)) + 1;
    this.sesiones.push(sesion);
  }

  editarSesion(sesion: Sesion) {
    let indice = this.sesiones.findIndex(c => c.id == sesion.id);
    this.sesiones[indice] = sesion;
  }

  eliminarSesion(id: number) {
    let indice = this.sesiones.findIndex(c => c.id == id);
    this.sesiones.splice(indice, 1);
  }
}
