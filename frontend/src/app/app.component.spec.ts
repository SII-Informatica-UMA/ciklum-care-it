import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Plan } from './plan';
import { PlanesService } from './planes.service';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let compiled: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule
      ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    compiled = fixture.nativeElement as HTMLElement;
  });

  it('debe crear la aplicación', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`debe tener como título 'Sesiones'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.planElegido).toBeUndefined();
  });

  it('debe renderizar el titulo', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.container h1')?.textContent).toContain('Lista de planes');
  });

  it('debe seleccionar un plan', () => {
    const plan: Plan = { id: 1, idRutina: 1, reglaRecurrencia: '', fechaInicio: new Date(), fechaFin: new Date() };
    component.elegirPlan(plan);
    expect(component.planElegido).toEqual(plan);
  });

  it('debe ordenar por fecha', () => {
    const planes: Plan[] = [
      { id: 3, idRutina: 3, reglaRecurrencia: '', fechaInicio: new Date('2025-03-29T08:00:00'), fechaFin: new Date('2026-03-29T08:00:00') },
      { id: 2, idRutina: 2, reglaRecurrencia: '', fechaInicio: new Date('2024-03-29T08:00:00'), fechaFin: new Date('2025-03-29T08:00:00') },
      { id: 1, idRutina: 1, reglaRecurrencia: '', fechaInicio: new Date('2026-03-29T08:00:00'), fechaFin: new Date('2027-03-29T08:00:00') }
    ];

    const planesService = TestBed.inject(PlanesService);
    planesService.ordenarPlanes(planes);

    expect(planes[0].id).toBe(2);
    expect(planes[1].id).toBe(3);
    expect(planes[2].id).toBe(1);
  });

  it('debe haber tantos botones como planes', () => {
    const planes: Plan[] = [
      { id: 1, idRutina: 1, reglaRecurrencia: '', fechaInicio: new Date(), fechaFin: new Date() },
      { id: 2, idRutina: 2, reglaRecurrencia: '', fechaInicio: new Date(), fechaFin: new Date() },
      { id: 3, idRutina: 3, reglaRecurrencia: '', fechaInicio: new Date(), fechaFin: new Date() }
    ];
    component.planes = planes;
  
    fixture.detectChanges();
    const botones = compiled.querySelectorAll('button');
    expect(botones.length).toEqual(planes.length);
  });

});
