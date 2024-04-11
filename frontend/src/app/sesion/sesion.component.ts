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

  private actualizaSesiones(id?: number): void {
    this.sesionesService.getSesiones(this.planId!)      
    .subscribe((res: Sesion[]) => {
      this.sesiones = res;
      this.sesionesService.ordenarSesiones(this.sesiones);

      if (id) {
        this.sesionElegida = this.sesiones.find(c => c.id == id);
      }
    });
  }

  aniadirSesion(): void {
    let ref = this.modalService.open(FormularioSesionComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.sesion = {idPlan: this.planId, inicio: '', fin: '', trabajoRealizado: '', multimedia: ["",""], descripcion: '', presencial: false, datosSalud: ["","",""], id: 0};
    ref.result.then((sesionAnadida: Sesion) => {
      let sesion = {...sesionAnadida};//Para evitar warnings al modificar las fechas
      if(sesion.inicio) sesion.inicio = new Date(sesion.inicio).toISOString();
      if(sesion.fin) sesion.fin = new Date(sesion.fin).toISOString();
      this.sesionesService.addSesion(sesion, this.planId!)      
      .subscribe(res => {
        this.actualizaSesiones(this.sesionElegida?.id);
      });
    });

  }

  eliminarSesion(id: number): void {
    this.sesionesService.eliminarSesion(id)    
    .subscribe(res => {
      this.actualizaSesiones();
    });;
    this.sesionElegida = undefined;
  }

 

  editarSesion(sesion: Sesion): void {
    this.sesionesService.editarSesion(sesion)
    .subscribe(res => {
      this.actualizaSesiones(sesion.id)
    });
   
  }

  
}