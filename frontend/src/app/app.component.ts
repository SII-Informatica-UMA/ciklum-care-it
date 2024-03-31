import { Component, OnInit } from '@angular/core';
import { Plan } from './plan';
import { PlanesService } from './planes.service';
import { NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  planes: Plan [] = [];
  planElegido?: Plan;

  constructor(private planesService: PlanesService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.planes = this.planesService.getPlanes();
  }

  elegirPlan(plan: Plan): void {
    this.planElegido = plan;
  }
}
