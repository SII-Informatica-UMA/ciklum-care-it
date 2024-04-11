import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DetalleSesionComponent } from './detalle-sesion.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

class MockNgbModal {
  modalRef = {
    componentInstance: {
      sesion: {idPlan: 0, inicio: '', fin: '', 
      trabajoRealizado: '', multimedia: ["",""],
      descripcion: '', presencial: true, datosSalud: ["","",""], id: 0},
      accion: 'Añadir'},
    result: Promise.resolve({idPlan: 0, inicio: '', fin: '', 
    trabajoRealizado: '', multimedia: ["",""],
    descripcion: '', presencial: true, datosSalud: ["","",""], id: 0})};

  open() {
    return this.modalRef;
  }
}

describe('DetalleSesionComponent', () => {
  let mockModal: MockNgbModal;
  let component: DetalleSesionComponent;
  let fixture: ComponentFixture<DetalleSesionComponent>;
  let compiled: HTMLElement;

  beforeEach(async () => {
    mockModal = new MockNgbModal();

    await TestBed.configureTestingModule({
      declarations: [ DetalleSesionComponent ],
      imports: [FormsModule],
      providers: [
        {provide: NgbModal, useValue: mockModal}
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleSesionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    compiled = fixture.nativeElement as HTMLElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('debe haber dos botones', () => {
    expect(compiled.querySelectorAll('button')).toHaveSize(2);
  });

  it('el segundo botón debe editar el contacto', (done: DoneFn) => {
    const botonEditar = compiled.querySelector('.botones button:nth-child(2)') as HTMLElement
    component.sesion = {idPlan: 0, inicio: '', fin: '', 
    trabajoRealizado: '', multimedia: ["",""],
    descripcion: '', presencial: true, datosSalud: ["","",""], id: 0};
    const spyEditar = spyOn(component.sesionEditada, 'emit').and.callThrough();

    botonEditar.click();
    fixture.detectChanges();
    fixture.whenStable().then(() => {
      expect(spyEditar).toHaveBeenCalled();
      done();
    });
  });

  it('el primer botón debe eliminar el contacto', (done: DoneFn) => {
    const botonEditar = compiled.querySelector('.botones button:nth-child(1)') as HTMLElement
    const spyEditar = spyOn(component.sesionEliminada, 'emit').and.callThrough();

    botonEditar.click();
    fixture.detectChanges();
    fixture.whenStable().then(() => {
      expect(spyEditar).toHaveBeenCalled();
      done();
    });
  });

  it('se cambia el formato de la fechas antes de pasárselas al formulario de editar', (done: DoneFn) => {
    const botonEditar = compiled.querySelector('.botones button:nth-child(2)') as HTMLElement
    component.sesion = {idPlan: 0, inicio: '3000-10-29T08:00:00', fin: '3000-10-30T08:00:00', 
    trabajoRealizado: '', multimedia: ["",""],
    descripcion: '', presencial: true, datosSalud: ["","",""], id: 0};
    const spyEditar = spyOn(component, 'convertirFechaHora').and.callThrough();

    botonEditar.click();
    fixture.detectChanges();
    fixture.whenStable().then(() => {
      expect(spyEditar).toHaveBeenCalledTimes(2);
      done();
    });
  });

  it('se transforma a isostring las fechas devueltas por el formulario editar', (done: DoneFn) => {
    const botonEditar = compiled.querySelector('.botones button:nth-child(2)') as HTMLElement
    component.sesion = {idPlan: 0, inicio: '', fin: '', 
    trabajoRealizado: '', multimedia: ["",""],
    descripcion: '', presencial: true, datosSalud: ["","",""], id: 0};
    mockModal.modalRef.result = Promise.resolve({idPlan: 0, inicio: '3000-10-29T08:00:00', fin: '3000-10-30T08:00:00', 
    trabajoRealizado: '', multimedia: ["",""],
    descripcion: '', presencial: true, datosSalud: ["","",""], id: 0});
    const spyEditar = spyOn(Date.prototype, 'toISOString').and.callThrough();

    botonEditar.click();
    fixture.detectChanges();
    fixture.whenStable().then(() => {
      expect(spyEditar).toHaveBeenCalledTimes(2);
      done();
    });
  });
});
