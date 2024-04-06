import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DetalleSesionComponent } from './detalle-sesion.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

describe('DetalleSesionComponent', () => {
  let component: DetalleSesionComponent;
  let fixture: ComponentFixture<DetalleSesionComponent>;

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
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
**/