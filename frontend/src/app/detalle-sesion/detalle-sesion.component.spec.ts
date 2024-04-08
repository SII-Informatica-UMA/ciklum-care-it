import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DetalleSesionComponent } from './detalle-sesion.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

describe('DetalleSesionComponent', () => {
  let component: DetalleSesionComponent;
  let fixture: ComponentFixture<DetalleSesionComponent>;
  let compiled: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetalleSesionComponent ],
      imports: [FormsModule],
      providers: [NgbActiveModal]
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
});
