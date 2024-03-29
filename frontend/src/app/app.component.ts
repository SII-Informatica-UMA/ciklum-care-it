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
  sesiones: Sesion [] = [];
  sesionElegido?: Sesion;

  constructor(private sesionesService: SesionesService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.sesiones = this.sesionesService.getSesiones();
  }

  elegirSesion(sesion: Sesion): void {
    this.sesionElegido = sesion;
  }

  aniadirSesion(): void {
    let ref = this.modalService.open(FormularioSesionComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.sesion = {id: 0, nombre: '', apellidos: '', email: '', telefono: ''};
    ref.result.then((sesion: Sesion) => {
      this.sesionesService.addSesion(sesion);
      this.sesiones = this.sesionesService.getSesiones();
    }, (reason) => {});

  }
  sesionEditada(sesion: Sesion): void {
    this.sesionesService.editarSesion(sesion);
    this.sesiones = this.sesionesService.getSesiones();
    this.sesionElegido = this.sesiones.find(c => c.id == sesion.id);
  }

  eliminarSesion(id: number): void {
    this.sesionesService.eliminarSesion(id);
    this.sesiones = this.sesionesService.getSesiones();
    this.sesionElegido = undefined;
  }
}
