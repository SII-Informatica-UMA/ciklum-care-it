import { Component, Input, OnInit } from '@angular/core';
import {Sesion } from '../sesion';
import {SesionesService } from '../sesiones.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioSesionComponent} from '../formulario-sesion/formulario-sesion.component'
import {Plan} from '../plan';

@Component({
  selector: 'app-root',
  templateUrl: './sesion.component.html',
  styleUrls: ['./sesion.component.css']
})

export class SesionComponent implements OnInit {
  sesiones: Sesion [] = [];
  sesionElegida?: Sesion;
  @Input() plan?: Plan;

  constructor(private sesionesService: SesionesService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.sesiones = this.sesionesService.getSesiones(this?.plan.id);
  }

  elegirSesion(sesion: Sesion): void {
    this.sesionElegida = sesion;
  }

  aniadirSesion(): void {
    let ref = this.modalService.open(FormularioSesionComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.contacto = {idPlan: this.plan?.id, inicio: '', fin: '', trabajoRealizado: '', multimedia: [''], descripcion: '', presencial: false, datosSalud: [''], id: 0};
    ref.result.then((sesion: Sesion) => {
      this.sesionesService.addSesion(sesion);
      this.sesiones = this.sesionesService.getSesiones(this.plan?.id);
    }, (reason) => {});

  }

  eliminarSesion(id: number): void {
    this.sesionesService.eliminarSesion(id);
    this.sesiones = this.sesionesService.getSesiones(this.plan?.id);
    this.sesionElegida = undefined;
    
  }

  editarSesion(sesion: Sesion): void {
    let ref = this.modalService.open(FormularioSesionComponent);
    ref.componentInstance.accion = "Editar";
    ref.componentInstance.contacto = {...sesion};
    ref.result.then((sesion: Sesion) => {
      this.sesionesService.editarSesion(sesion);
    }, (reason) => {});
  }
}