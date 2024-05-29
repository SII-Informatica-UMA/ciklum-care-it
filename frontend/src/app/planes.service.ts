import { Injectable } from '@angular/core';
import {Plan } from './plan';
import { Subject,Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class PlanesService {
  private clienteId = 1;

  private planesEjemplo: Plan [] = [
    {id: 3, idRutina: 3, reglaRecurrencia: "" , fechaInicio: new Date('2026-03-29T08:00:00'), fechaFin: new Date('2027-03-29T08:00:00')},
    {id: 2, idRutina: 2, reglaRecurrencia: "" , fechaInicio: new Date('2025-03-29T08:00:00'), fechaFin: new Date('2026-03-29T08:00:00')},
    {id: 1, idRutina: 1, reglaRecurrencia: "" , fechaInicio: new Date('2024-03-29T08:00:00'), fechaFin: new Date('2025-03-29T08:00:00')}
  ];

  private planCambiadoSource = new Subject<void>();

  planCambiado$ = this.planCambiadoSource.asObservable();

  constructor(private http: HttpClient) {
    /*
  this.http.post('http://localhost:8080/centro',{nombre: 'Centro de entrenamiento',
  direccion: 'direccion'
  })
  .subscribe(c => {

  });

  this.http.post('http://localhost:8080/usuario',{nombre: 'Enrique',
  apellido1: 'P',
  apellido2: 'P',
  email: 'enrique@gmail.com',
  password: '',
  administrador: false
  })
  .subscribe(c => {

  });

  this.http.post('http://localhost:8080/usuario',{nombre: 'Alberto',
  apellido1: 'T',
  apellido2: 'T',
  email: 'alberto@gmail.com',
  password: '',
  administrador: false
  })
  .subscribe(c => {

  });

  this.http.post('http://localhost:8080/cliente?centro=1',{
  idUsuario:3,
  telefono: '111',
  direccion: 'direccion1',
  dni: '123123',
  fechaNacimiento: '2024-04-09T07:32:06.453Z',
  sexo: 'HOMBRE'
  })
  .subscribe(c => {

  });

  this.http.post('http://localhost:8080/entrenador?centro=1',{
    idUsuario: 4,
    telefono: "string",
    direccion: "string",
    dni: "string",
    fechaNacimiento: "2024-04-09T07:35:42.509Z",
    fechaAlta: "2024-04-09T07:35:42.509Z",
    fechaBaja: "2024-04-09T07:35:42.509Z",
    especialidad: "string",
    titulacion: "string",
    experiencia: "string",
    observaciones: "string"
  })
  .subscribe(c => {

  });

  this.http.post('http://localhost:8080/entrena?entrenador=1',{
    idEntrenador:1,
    idCliente:1,
    especialidad:"especialidad"
  })
  .subscribe(c => {

  });

  for(let i=0; i<this.planesEjemplo.length; i++){
       this.http.post('http://localhost:8080/plan?entrena=1',this.planesEjemplo[i])
      .subscribe(c => { });
  }*/
  }

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzE2MDMzNTQ4LCJleHAiOjE3MTg2MjU1NDh9.0EMk13GFbgr5vN2Si-pJrPQemHxzfbpq0kFJYExgsSY-K6p-J-LzeEjWhSFkw4WhIoWuKZKSQkupY-Y7FKaxjg`
    });
  }

  getPlanes(): Observable<any[]> {
    const headers = this.getHeaders();
    return this.http.get<any[]>('http://localhost:8084/entrena?cliente=' + this.clienteId, { headers });
  }

  planCambiado() {
    this.planCambiadoSource.next();
  }

  ordenarPlanes(planes: Plan[]){
    planes.sort(this.ordenarPorFecha);
  }

  ordenarPorFecha(a:Plan, b:Plan){
    if(a.fechaInicio < b.fechaInicio) return -1;
    if(a.fechaInicio > b.fechaInicio) return 1;
    return 0;
  }
   
}
