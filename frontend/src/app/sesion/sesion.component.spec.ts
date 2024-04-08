import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SesionComponent } from './sesion.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import {SesionesService } from '../sesiones.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Sesion} from '../sesion';
import { BrowserModule } from '@angular/platform-browser';

class MockNgbModal {
  modalRef = {
    componentInstance: {
      sesion: {idPlan: 0, inicio: '', fin: '', trabajoRealizado: '', multimedia: [''], descripcion: '', presencial: false, datosSalud: [''], id: 0},
      accion: 'Añadir'},
    result: Promise.resolve({idPlan: 0, inicio: '', fin: '', trabajoRealizado: '', multimedia: [''], descripcion: '', presencial: false, datosSalud: [''], id: 0})};

  open() {
    return this.modalRef;
  }
}

describe('SesionComponent', () => {
  let mockService: SesionesService;
  let mockModal: MockNgbModal;
  let component: SesionComponent;
  let fixture: ComponentFixture<SesionComponent>;
  let compiled: HTMLElement;
  let eddd=0

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SesionComponent],
      imports: [FormsModule],
      providers: [NgbActiveModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SesionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
