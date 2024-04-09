import { Component, OnInit} from '@angular/core';
import { Plan } from './plan';
import { Sesion } from './sesion';
import { PlanesService } from './planes.service';
import { SesionesService } from './sesiones.service';
import { NgbModal} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  planes: Plan [] = [];
  sesionesPlan: Sesion [] = [];
  planElegido?: Plan;

  constructor(private sesionesService: SesionesService, private planesService: PlanesService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.planesService.getPlanes()      
    .subscribe((res: any[]) => {
      for (let i = 0; i < res.length; i++) {
        this.planes.push(...res[i].planes);
      }
      this.planesService.ordenarPlanes(this.planes);
    });
  }

  elegirPlan(plan: Plan): void {
    this.planElegido = plan;
    this.sesionesPlan = [];
    this.sesionesService.getSesiones(plan.id)      
    .subscribe((res: Sesion[]) => {
      this.sesionesPlan = res;
      this.sesionesService.ordenarSesiones(this.sesionesPlan);
    });
    this.planesService.planCambiado();
  }
}
