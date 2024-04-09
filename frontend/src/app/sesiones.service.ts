import { Injectable } from '@angular/core';
import {Sesion } from './sesion';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class SesionesService {

  private clienteId = 1;

  private sesionesEjemplo: Sesion [] = [
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

  private sesiones: Sesion [] = [];

  private sesionCambiadoSource = new Subject<void>();

  sesionCambiado$ = this.sesionCambiadoSource.asObservable();

  constructor(private http: HttpClient) {/*

    for(let i=0; i<this.sesionesEjemplo.length; i++){
      this.http.post('http://localhost:8080/sesion?plan=' + this.sesionesEjemplo[i].idPlan, this.sesionesEjemplo[i])
        .subscribe(c => {
      });
   }*/

   }

  getSesiones(idPlan: number): Sesion [] {

    this.sesiones=[];

     this.http.get<any[]>('http://localhost:8080/sesion?plan=' + idPlan)
      .subscribe((res: any[]) => {
        for (let i = 0; i < res.length; i++) {
          this.sesiones.push(res[i]);
          this.ordenarSesiones();
        }
      });

      return this.sesiones;
  }

  addSesion(sesion: Sesion, plan_id: number) {
    sesion.id = Math.max(...this.sesiones.map(c => c.id)) + 1;
    this.http.post('http://localhost:8080/sesion?plan=' + plan_id, sesion)
      .subscribe((response: any) => {
        this.sesiones.push(response); 
        this.ordenarSesiones();
      });
  }

  editarSesion(sesion: Sesion) {
    let indice = this.sesiones.findIndex(c => c.id == sesion.id);
    this.sesiones[indice] = sesion;
  }

  eliminarSesion(id: number) {
    let indice = this.sesiones.findIndex(c => c.id == id);
    this.http.delete('http://localhost:8080/sesion/' + id, {observe: "response", responseType: 'text'}).subscribe((response: any) => {
      this.sesiones.splice(indice,1); 
    });;
  }

  ordenarSesiones(){
    this.sesiones.sort(this.ordenarPorFecha);
  }

  ordenarPorFecha(a:Sesion, b:Sesion){
    if(new Date(a.inicio) < new Date(b.inicio)) return -1;
    if(new Date(a.inicio) > new Date(b.inicio)) return 1;
    return 0;
  }
}
