package sesiones.backend.controllers;

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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sesion")
public class SesionController {
    private SesionService servicio;

    public SesionController(SesionService servicioSesion) {
        servicio = servicioSesion;
    }

    @GetMapping
	public ResponseEntity<List<SesionDTO>> listaDeSesiones() {
		return ResponseEntity.ok(servicio.getTodasSesiones().stream()
			.map(Mapper::toSesionDTO)
			.toList());
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

    @GetMapping("{id}")
	public ResponseEntity<SesionDTO> obtenerSesion(@PathVariable(name = "id") Long id) {
		try {
			Optional<Sesion> sesion = servicio.obtenerSesion(id);
			return ResponseEntity.of(sesion.map(Mapper::toSesionDTO));
		} catch(SesionNoAsociadaException e){
			return ResponseEntity.status(404).build();
		}
	}

}
