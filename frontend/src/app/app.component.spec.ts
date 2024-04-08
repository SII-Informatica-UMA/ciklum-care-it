import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let compiled: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
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
  
  it('debe haber tres botones', () => {
    expect(compiled.querySelectorAll('button')).toHaveSize(3);
  });

  it('debe mostrar el plan con fecha de inicio menor en primer lugar', () => {
    expect(compiled.querySelector('.list-group button:first-child')?.textContent).toBe('29/3/2024, 8:00:00 - 29/3/2025, 8:00:00');
  });

  it('debe mostrar el plan con fecha de inicio intermedia en segundo lugar', () => {
    expect(compiled.querySelector('.list-group button:nth-child(2)')?.textContent).toBe('29/3/2025, 8:00:00 - 29/3/2026, 8:00:00');
  });

  it('debe mostrar el plan con fecha de inicio final en tercer lugar', () => {
    expect(compiled.querySelector('.list-group button:nth-child(3)')?.textContent).toBe('29/3/2026, 8:00:00 - 29/3/2027, 8:00:00');
  });

});
