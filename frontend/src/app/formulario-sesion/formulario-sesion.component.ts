import { Component } from '@angular/core';
import  {Sesion} from '../sesion';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-formulario-sesion',
  templateUrl: './formulario-sesion.component.html',
  styleUrls: ['./formulario-sesion.component.css']
})
export class FormularioSesionComponent {
  accion?: "Añadir" | "Editar";
  sesion: Sesion = {idPlan: 0, inicio: '', fin: '', 
	trabajoRealizado: '', multimedia: [],
	descripcion: '', presencial: true, datosSalud: [], id: 0};

  constructor(public modal: NgbActiveModal) { }

  guardarSesion(): void {
    this.modal.close(this.sesion);
  }

}
