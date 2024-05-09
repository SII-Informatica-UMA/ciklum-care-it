package sesiones.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import sesiones.backend.dtos.SesionDTO;
import sesiones.backend.dtos.SesionNuevaDTO;
import sesiones.backend.entities.Sesion;
import sesiones.backend.exceptions.SesionNoAsociadaException;
import sesiones.backend.services.SesionService;

import java.net.URI;

@RestController
@RequestMapping("/sesion")
public class SesionController {
    private SesionService servicio;

    public SesionController(SesionService servicioSesion) {
        servicio = servicioSesion;
    }

    @PostMapping
    public ResponseEntity<SesionDTO> aniadirSesion(@RequestBody SesionNuevaDTO sesion, @RequestParam("plan") Long planId, UriComponentsBuilder builder) {
        try{
            Sesion sesionAux = Mapper.toSesion(sesion);
            sesionAux.setIdPlan(planId);
            sesionAux = servicio.aniadirSesion(sesionAux);

            SesionDTO sesionDTO = Mapper.toSesionDTO(sesionAux);
            URI uri = builder
                    .path("/sesion")
                    .path(String.format("/%d", sesionDTO.getId()))
                    .build()
                    .toUri();
            return ResponseEntity.created(uri).body(sesionDTO);
        }catch(SesionNoAsociadaException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
