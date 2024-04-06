import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SesionComponent } from './sesion.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

describe('SesionComponent', () => {
  let component: SesionComponent;
  let fixture: ComponentFixture<SesionComponent>;

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
