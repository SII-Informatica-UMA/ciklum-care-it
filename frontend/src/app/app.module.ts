import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DetalleSesionComponent } from './detalle-sesion/detalle-sesion.component';
import { FormularioSesionComponent } from './formulario-sesion/formulario-sesion.component';

@NgModule({
  declarations: [
    AppComponent,
    DetalleSesionComponent,
    FormularioSesionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
