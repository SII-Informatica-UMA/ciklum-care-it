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
    component.planes = [
      { id: 3, idRutina: 3, reglaRecurrencia: '', fechaInicio: new Date('2025-03-29T08:00:00'), fechaFin: new Date('2026-03-29T08:00:00') },
      { id: 2, idRutina: 2, reglaRecurrencia: '', fechaInicio: new Date('2024-03-29T08:00:00'), fechaFin: new Date('2025-03-29T08:00:00') },
      { id: 1, idRutina: 1, reglaRecurrencia: '', fechaInicio: new Date('2026-03-29T08:00:00'), fechaFin: new Date('2027-03-29T08:00:00') }
    ];
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

  it('debe renderizar el titulo correcto', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.rounded h1')?.textContent).toContain('Planes');
  });

  it('debe seleccionar un plan si es elegido', () => {
    component.elegirPlan(component.planes[0]);
    expect(component.planElegido).toEqual(component.planes[0]);
  });

  it('debe ordenar por fecha', () => {
    const planesService = TestBed.inject(PlanesService);
    planesService.ordenarPlanes(component.planes);

    expect(component.planes[0].id).toBe(2);
    expect(component.planes[1].id).toBe(3);
    expect(component.planes[2].id).toBe(1);
  });

  it('debe haber tantos botones como planes', () => {
    const botones = compiled.querySelectorAll('button');
    expect(botones.length).toEqual(component.planes.length);
  });
});
