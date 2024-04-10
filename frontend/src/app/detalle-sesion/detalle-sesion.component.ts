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
    if(ref.componentInstance.sesion.inicio) ref.componentInstance.sesion.inicio = this.convertirFechaHora(ref.componentInstance.sesion.inicio);
    if(ref.componentInstance.sesion.fin) ref.componentInstance.sesion.fin = this.convertirFechaHora(ref.componentInstance.sesion.fin);
    ref.result.then((sesionEditada: Sesion) => {
      let sesion ={...sesionEditada}; //Para evitar warnings al modificar las fechas
      if(sesion.inicio) sesion.inicio = new Date(sesion.inicio).toISOString();
      if(sesion.fin) sesion.fin = new Date(sesion.fin).toISOString();
      this.sesionEditada.emit(sesion);
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
