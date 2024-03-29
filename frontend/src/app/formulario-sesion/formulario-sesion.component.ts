import { Component } from '@angular/core';
import  {Contacto} from '../contacto';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-formulario-sesion',
  templateUrl: './formulario-sesion.component.html',
  styleUrls: ['./formulario-sesion.component.css']
})
export class FormularioSesionComponent {
  accion?: "Añadir" | "Editar";
  contacto: Contacto = {id: 0, nombre: '', apellidos: '', email: '', telefono: ''};

  constructor(public modal: NgbActiveModal) { }

  guardarContacto(): void {
    this.modal.close(this.contacto);
  }

}
