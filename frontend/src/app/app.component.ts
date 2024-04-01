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
  planElegido?: Plan

  constructor(private sesionesService: SesionesService, private planesService: PlanesService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.planes = this.planesService.getPlanes();
  }

  elegirPlan(plan: Plan): void {
    this.planElegido = plan;
    this.sesionesPlan = this.sesionesService.getSesiones(plan.id);
    this.planesService.planCambiado();
  }
}
