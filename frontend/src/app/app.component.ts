import { Component, OnInit } from '@angular/core';
import {Sesion } from './sesion';
import {SesionesService } from './sesiones.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioSesionComponent} from './formulario-sesion/formulario-sesion.component'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  idPlan: number = 1;
  sesiones: Sesion [] = [];
  sesionElegido?: Sesion;

  constructor(private sesionesService: SesionesService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.sesiones = this.sesionesService.getSesiones(this.idPlan);
  }

  elegirSesion(sesion: Sesion): void {
    this.sesionElegido = sesion;
  }

  aniadirSesion(): void {
    let ref = this.modalService.open(FormularioSesionComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.sesion = {idPlan: this.idPlan, inicio: new Date(), fin: new Date(), 
	trabajoRealizado: '', multimedia: [],
	descripcion: '', presencial: true, datosSalud: [], id: 0};
    ref.result.then((sesion: Sesion) => {
      this.sesionesService.addSesion(sesion);
      this.sesiones = this.sesionesService.getSesiones(this.idPlan);
    }, (reason) => {});

  }
  sesionEditada(sesion: Sesion): void {
    this.sesionesService.editarSesion(sesion);
    this.sesiones = this.sesionesService.getSesiones(this.idPlan);
    this.sesionElegido = this.sesiones.find(c => c.id == sesion.id);
  }

  eliminarSesion(id: number): void {
    this.sesionesService.eliminarSesion(id);
    this.sesiones = this.sesionesService.getSesiones(this.idPlan);
    this.sesionElegido = undefined;
  }
}
