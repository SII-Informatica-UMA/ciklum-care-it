import { Injectable } from '@angular/core';
import {Sesion } from './sesion';

@Injectable({
  providedIn: 'root'
})
export class SesionesService {
  private sesiones: Sesion [] = [
    {id: 1, nombre: 'Juan', apellidos: 'Pérez', email: 'perez@uma.es', telefono: '666666666'},
    {id: 2, nombre: 'Ana', apellidos: 'García', email: 'ana@uma.es', telefono: '55555555'},
    {id: 3, nombre: 'Luis', apellidos: 'González', email: 'gonzalez@uma.es', telefono: '444444444'},
  ];

  constructor() { }

  getSesiones(): Sesion [] {
    return this.sesiones;
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
