import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormularioSesionComponent } from './formulario-sesion.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

describe('FormularioSesionComponent', () => {
  let component: FormularioSesionComponent;
  let fixture: ComponentFixture<FormularioSesionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormularioSesionComponent],
      imports: [FormsModule],
      providers: [NgbActiveModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormularioSesionComponent);
    component = fixture.componentInstance;
    component.accion = 'Añadir';
    component.sesion = {idPlan: 1, inicio: '2024-03-29T08:00:00', fin: '2024-03-29T09:00:00', 
    trabajoRealizado: 'Trabajo realizado', multimedia: ['enlace1', 'enlace2'],
    descripcion: 'Descripción1', presencial: true, datosSalud: ['80', '60', '254'], id: 1};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('debe mostrar el campo de fin con la fecha correcta (pobando con YYYY-MM-DDTHH:mm:ss )', (done: DoneFn) => {
    const compiled = fixture.nativeElement as HTMLElement;
    const campo = compiled.querySelector('#fin') as HTMLInputElement;

    fixture.whenStable().then(() => {
      expect(campo.value).toBe('2024-03-29T09:00');
      done(); 
    });
  });

  it('debe mostrar el campo de inicio con la fecha correcta (pobando con YYYY-MM-DDTHH:mm:ss )', (done: DoneFn) => {
    const compiled = fixture.nativeElement as HTMLElement;
    const campo = compiled.querySelector('#inicio') as HTMLInputElement;

    fixture.whenStable().then(() => {
      expect(campo.value).toBe('2024-03-29T08:00');
      done(); 
    });
  });

  it('debe mostrar el campo trabajo realizado (pobando con una string)', (done: DoneFn) => {
    const compiled = fixture.nativeElement as HTMLElement;
    const campo = compiled.querySelector('#trabajo') as HTMLInputElement;

    fixture.whenStable().then(() => {
      expect(campo.value).toBe('Trabajo realizado');
      done();
    });
  });

  it('debe mostrar el campo video (pobando con una string)', (done: DoneFn) => {
    const compiled = fixture.nativeElement as HTMLElement;
    const campo = compiled.querySelector('#video') as HTMLInputElement;

    fixture.whenStable().then(() => {
      expect(campo.value).toBe('enlace1');
      done();
    });
  });

  it('debe mostrar el campo foto (pobando con una string)', (done: DoneFn) => {
    const compiled = fixture.nativeElement as HTMLElement;
    const campo = compiled.querySelector('#foto') as HTMLInputElement;

    fixture.whenStable().then(() => {
      expect(campo.value).toBe('enlace2');
      done();
    });
  });

  it('debe mostrar el campo descripcion (pobando con una string)', (done: DoneFn) => {
    const compiled = fixture.nativeElement as HTMLElement;
    const campo = compiled.querySelector('#descripcion') as HTMLInputElement;

    fixture.whenStable().then(() => {
      expect(campo.value).toBe('Descripción1');
      done();
    });
  });

  it('debe mostrar el campo pulsaciones (pobando con una string)', (done: DoneFn) => {
    const compiled = fixture.nativeElement as HTMLElement;
    const campo = compiled.querySelector('#pulsaciones') as HTMLInputElement;

    fixture.whenStable().then(() => {
      expect(campo.value).toBe('80');
      done();
    });
  });

  it('debe mostrar el campo peso (pobando con una string)', (done: DoneFn) => {
    const compiled = fixture.nativeElement as HTMLElement;
    const campo = compiled.querySelector('#peso') as HTMLInputElement;

    fixture.whenStable().then(() => {
      expect(campo.value).toBe('60');
      done();
    });
  });

  it('debe mostrar el campo calorias (pobando con una string)', (done: DoneFn) => {
    const compiled = fixture.nativeElement as HTMLElement;
    const campo = compiled.querySelector('#calorias') as HTMLInputElement;

    fixture.whenStable().then(() => {
      expect(campo.value).toBe('254');
      done();
    });
  });

  it('debe mostrar el campo presencial (pobando con true)', (done: DoneFn) => {
    const compiled = fixture.nativeElement as HTMLElement;
    const campo = compiled.querySelector('#presencial') as HTMLInputElement;

    fixture.whenStable().then(() => {
      expect(campo.checked).toBe(true);
      done();
    });
  });

  it('debe modificar el valor presencial (clickeando en la checkbox cuando está en true)', (done: DoneFn) => {
    const compiled = fixture.nativeElement as HTMLElement;
    const campo = compiled.querySelector('#presencial') as HTMLInputElement;
    campo.click();
    campo.dispatchEvent(new Event('change'));
    fixture.detectChanges();

    fixture.whenStable().then(() => {
      expect(component.sesion.presencial).toBe(false);
      done();
    });
  });


  
});


