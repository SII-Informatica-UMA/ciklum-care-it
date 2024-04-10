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
    ref.componentInstance.sesion.multimedia = [...this.sesion!.multimedia];
    ref.componentInstance.sesion.datosSalud = [...this.sesion!.datosSalud];
    ref.componentInstance.sesion.inicio = this.convertirFechaHora(ref.componentInstance.sesion.inicio);
    ref.componentInstance.sesion.fin = this.convertirFechaHora(ref.componentInstance.sesion.fin);
    ref.result.then((sesionEditada: Sesion) => {
      sesionEditada.inicio = new Date(sesionEditada.inicio).toISOString();
      sesionEditada.fin = new Date(sesionEditada.fin).toISOString();
      this.sesionEditada.emit(sesionEditada);
    }, (reason) => {});
  }

  eliminarSesion(): void {
    this.sesionEliminada.emit(this.sesion?.id);
  }

  convertirFechaHora(fechaHora: string): string {
    const fecha = new Date(fechaHora);

    // Formatear la fecha y hora en el formato YYYY-MM-DDTHH:MM
    const anio = fecha.getFullYear();
    const mes = ('0' + (fecha.getMonth() + 1)).slice(-2); // Ajustar el mes para que tenga dos dígitos
    const dia = ('0' + fecha.getDate()).slice(-2); // Ajustar el día para que tenga dos dígitos
    const hora = ('0' + fecha.getHours()).slice(-2); // Ajustar la hora para que tenga dos dígitos
    const minutos = ('0' + fecha.getMinutes()).slice(-2); // Ajustar los minutos para que tenga dos dígitos
    return `${anio}-${mes}-${dia}T${hora}:${minutos}`;
}
}
