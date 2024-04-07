import { Injectable } from '@angular/core';
import {Plan } from './plan';
import { Subject } from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Usuario} from './usuario';
import {Centro} from './centro';
import {Gerente} from './gerente';

@Injectable({
  providedIn: 'root'
})

export class PlanesService {
  private planes: Plan [] = [
    {id: 1, idRutina: 1, reglaRecurrencia: "" ,fechaInicio: new Date('2024-03-29T08:00:00'), fechaFin: new Date('2025-03-29T08:00:00')},
    {id: 2, idRutina: 2, reglaRecurrencia: "" , fechaInicio: new Date('2025-03-29T08:00:00'), fechaFin: new Date('2026-03-29T08:00:00')},
    {id: 3, idRutina: 3, reglaRecurrencia: "" , fechaInicio: new Date('2026-03-29T08:00:00'), fechaFin: new Date('2027-03-29T08:00:00')},
  ];

  private planCambiadoSource = new Subject<void>();

  planCambiado$ = this.planCambiadoSource.asObservable();

  constructor(private http: HttpClient) { }

  getPlanes(): Plan [] {
    this.http.post<Usuario[]>('http://localhost:8080/usuario',{nombre: 'Enrique',
    apellido1: 'Perez',
    apellido2: 'Perez',
    email: 'enrique@gmail.com',
    password: '',
    administrador: true})
    .subscribe(contactos => {

    });

    this.http.get<Usuario[]>('http://localhost:8080/usuario')
    .subscribe(c => {

    });

    this.http.get<Usuario>('http://localhost:8080/usuario/3')
    .subscribe(c => {

    });

    this.http.get<Centro[]>('http://localhost:8080/centro')
    .subscribe(c => {

    });

    this.http.get<Gerente[]>('http://localhost:8080/gerente')
    .subscribe(c => {

    });

    this.http.post<Centro>('http://localhost:8080/centro',{nombre: 'centro',
    direccion: 'direccion'
    })
    .subscribe(c => {

    });

    this.http.post<Gerente>('http://localhost:8080/gerente',{idUsuario:1,empresa:'empresa'})
    .subscribe(contactos => {

    });
    return this.planes;
  }

  planCambiado() {
    this.planCambiadoSource.next();
  }
   
}
