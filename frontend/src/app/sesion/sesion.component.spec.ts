import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SesionComponent } from './sesion.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


describe('SesionComponent', () => {
  let component: SesionComponent;
  let fixture: ComponentFixture<SesionComponent>;
  let compiled: HTMLElement;
  let eddd=0

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SesionComponent],
      imports: [FormsModule,HttpClientModule],
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
