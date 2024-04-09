import { Component, Input, OnInit} from '@angular/core';
import {Sesion } from '../sesion';
import {SesionesService } from '../sesiones.service';
import {PlanesService } from '../planes.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioSesionComponent} from '../formulario-sesion/formulario-sesion.component'
import {Plan} from '../plan';

@Component({
  selector: 'app-sesion',
  templateUrl: './sesion.component.html',
  styleUrls: ['./sesion.component.css']
})

export class SesionComponent implements OnInit {
  @Input() sesiones: Sesion [] = [];
  @Input() planId?: number;
  sesionElegida?: Sesion;

  constructor(private planesService: PlanesService, private sesionesService: SesionesService, 
    private modalService: NgbModal) { }

  ngOnInit() {
    this.planesService.planCambiado$.subscribe(() => {
      this.sesionElegida = undefined;
    });
  }

  elegirSesion(sesion: Sesion): void {
    this.sesionElegida = sesion;
  }

  aniadirSesion(): void {
    let ref = this.modalService.open(FormularioSesionComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.sesion = {idPlan: this.planId, inicio: '', fin: '', trabajoRealizado: '', multimedia: [''], descripcion: '', presencial: false, datosSalud: [''], id: 0};
    ref.result.then((sesion: Sesion) => {
      this.sesionesService.addSesion(sesion, this.planId!);
      this.sesiones = this.sesionesService.getSesiones(this.planId!);
    }, (reason) => {});

  }

  eliminarSesion(id: number): void {
    this.sesionesService.eliminarSesion(id);
    this.sesiones = this.sesionesService.getSesiones(this.planId!);
    this.sesionElegida = undefined;
  }

  editarSesion(sesion: Sesion): void {
    this.sesionesService.editarSesion(sesion);
    this.sesiones = this.sesionesService.getSesiones(this.planId!);
	  this.sesionElegida = this.sesiones.find(c => c.id == sesion.id);
  }

  
}