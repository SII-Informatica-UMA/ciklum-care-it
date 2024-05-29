import { Injectable } from '@angular/core';
import {Sesion } from './sesion';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class SesionesService {

  private clienteId = 1;
  

  private sesionesEjemplo: Sesion [] = [
    {idPlan: 2, inicio: '3000-10-29T08:00:00', fin: '2024-10-29T09:00:00', 
	trabajoRealizado: 'Trabajo realizado', multimedia: ["",""],
	descripcion: 'Descripción4', presencial: true, datosSalud: ["","",""], id: 4},
    {idPlan: 1, inicio: '2024-03-29T08:00:00', fin: '2024-03-29T09:00:00', 
	trabajoRealizado: 'Trabajo realizado', multimedia: ["",""],
	descripcion: 'Descripción1', presencial: true, datosSalud: ["","",""], id: 1},
    {idPlan: 1, inicio: '2024-03-30T08:00:00', fin: '2024-03-30T09:00:00', 
	trabajoRealizado: 'Trabajo realizado', multimedia: ["",""],
	descripcion: 'Descripción2', presencial: false, datosSalud: ["","",""], id: 2},
    {idPlan: 2, inicio: '3000-03-29T08:00:00', fin: '2024-03-29T09:00:00', 
	trabajoRealizado: 'Trabajo realizado', multimedia: ["",""],
	descripcion: 'Descripción3', presencial: true, datosSalud: ["","",""], id: 3},
  {idPlan: 2, inicio: '3000-03-29T08:00:00', fin: '2024-05-29T09:00:00', 
	trabajoRealizado: 'AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH', multimedia: [],
	descripcion: 'Descripción5. La verdad es que no me ha gustado nada este entrenamiento. Me gustaria que dejasen de poner ese tipo de cosas porque me acaba doliendo la espalda.', presencial: true, datosSalud: [], id: 5},
  ];

  constructor(private http: HttpClient) {
    /*for(let i=0; i<this.sesionesEjemplo.length; i++){
      this.http.post('http://localhost:8080/sesion?plan=' + this.sesionesEjemplo[i].idPlan, this.sesionesEjemplo[i])
        .subscribe(c => {
      });
   }*/
  }

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzE2MDMzNTQ4LCJleHAiOjE3MTg2MjU1NDh9.0EMk13GFbgr5vN2Si-pJrPQemHxzfbpq0kFJYExgsSY-K6p-J-LzeEjWhSFkw4WhIoWuKZKSQkupY-Y7FKaxjg`
    });
  }

  getSesiones(idPlan: number): Observable<Sesion[]> {
    const headers = this.getHeaders();
    return this.http.get<Sesion[]>('http://localhost:8080/sesion?plan=' + idPlan, { headers });
  }

  addSesion(sesion: Sesion, plan_id: number): Observable<Sesion> {
    const headers = this.getHeaders();
    return this.http.post<Sesion>('http://localhost:8080/sesion?plan=' + plan_id, sesion, { headers });
  }

  editarSesion(sesion: Sesion): Observable<Sesion> {
     /*let indice = this.sesiones.findIndex(c => c.id == sesion.id);
    this.sesiones[indice] = sesion;*/
    const headers = this.getHeaders();
    return this.http.put<Sesion>('http://localhost:8080/sesion/' + sesion.id, sesion, { headers });
  }

  eliminarSesion(id: number): Observable<HttpResponse<string>> {
    const headers = this.getHeaders();
    return this.http.delete('http://localhost:8080/sesion/' + id, { headers, observe: 'response', responseType: 'text' });
  }

  ordenarSesiones(sesiones: Sesion[]){
    sesiones.sort(this.ordenarPorFecha);
  }

  ordenarPorFecha(a:Sesion, b:Sesion){
    if(new Date(a.inicio) < new Date(b.inicio)) return -1;
    if(new Date(a.inicio) > new Date(b.inicio)) return 1;
    return 0;
  }
}
