import { Component, Input, Output, EventEmitter } from '@angular/core';
import {Sesion } from '../sesion';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioSesionComponent} from '../formulario-sesion/formulario-sesion.component'
import { SesionesService } from '../sesiones.service';

@Component({
  selector: 'app-detalle-sesion',
  templateUrl: './detalle-sesion.component.html',
  styleUrls: ['./detalle-sesion.component.css']
})
export class DetalleSesionComponent {
  @Input() sesion?: Sesion;
  @Output() sesionEditada = new EventEmitter<Sesion>();
  @Output() sesionEliminada = new EventEmitter<number>();

  constructor(private modalService: NgbModal) { }

  editarSesion(): void {
    let ref = this.modalService.open(FormularioSesionComponent);
    ref.componentInstance.accion = "Editar";
    ref.componentInstance.sesion = {...this.sesion};
    ref.componentInstance.sesion.multimedia = {...this.sesion?.multimedia};
    ref.componentInstance.sesion.datosSalud = {...this.sesion?.datosSalud};
    ref.result.then((sesionEditada: Sesion) => {
      sesionEditada.multimedia = [sesionEditada.multimedia[0],sesionEditada.multimedia[1]];
      sesionEditada.datosSalud = [sesionEditada.datosSalud[0],sesionEditada.datosSalud[1],sesionEditada.datosSalud[2]];
      this.sesionEditada.emit(sesionEditada);
    }, (reason) => {});
  }

  eliminarSesion(): void {
    this.sesionEliminada.emit(this.sesion?.id);
  }
}
