package sesiones.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import sesiones.backend.dtos.SesionDTO;
import sesiones.backend.dtos.SesionNuevaDTO;
import sesiones.backend.entities.Sesion;
import sesiones.backend.exceptions.NoAutorizadoException;
import sesiones.backend.exceptions.SesionInexistenteException;
import sesiones.backend.services.SesionService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sesion")
public class SesionController {
    private final SesionService servicioSesion;

    public SesionController(SesionService servicioSesion) {
        this.servicioSesion = servicioSesion;
    }

    @GetMapping()
	public ResponseEntity<List<SesionDTO>> listaDeSesiones(@RequestParam(value="plan") Long planId) {
		return ResponseEntity.ok(servicioSesion.getSesiones(planId).stream()
			.map(Mapper::toSesionDTO)
			.toList());
	}

    @PostMapping()
    public ResponseEntity<SesionDTO> aniadirSesion(@RequestBody SesionNuevaDTO sesion, @RequestParam(value="plan") Long planId, UriComponentsBuilder builder) {
       
            Sesion sesionAux = Mapper.toSesion(sesion);
            sesionAux.setIdPlan(planId);
            sesionAux = servicioSesion.aniadirSesion(sesionAux);

            SesionDTO sesionDTO = Mapper.toSesionDTO(sesionAux);
            URI uri = builder
                    .path("/sesion")
                    .path(String.format("/%d", sesionDTO.getId()))
                    .build()
                    .toUri();
            return ResponseEntity.created(uri).body(sesionDTO);
    }

    @GetMapping("/{id}")
	public ResponseEntity<SesionDTO> obtenerSesion(@PathVariable(name = "id") Long id) {
		try {
			Optional<Sesion> sesion = servicioSesion.obtenerSesion(id);
			return ResponseEntity.of(sesion.map(Mapper::toSesionDTO));
		} catch(SesionInexistenteException e){
			return ResponseEntity.status(404).build();
		} catch(NoAutorizadoException e){
            return ResponseEntity.status(403).build();
        }
	}

    @PutMapping("/{id}")
	public ResponseEntity<SesionDTO> editarSesion(@PathVariable Long id, @RequestBody SesionDTO sesion) {
        sesion.setId(id);
		try {
			SesionDTO sesionEditada = Mapper.toSesionDTO(servicioSesion.editarSesion(Mapper.toSesion(sesion)));
			return ResponseEntity.ok(sesionEditada);
		} catch(SesionInexistenteException e){
			return ResponseEntity.status(404).build();
		} catch(NoAutorizadoException e){
            return ResponseEntity.status(403).build();
        }
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSesion(@PathVariable Long id) {
        try{
            servicioSesion.deleteSesion(id);
            return ResponseEntity.ok().build();
        } catch(SesionInexistenteException ne){
            return ResponseEntity.status(404).build();
        } catch(NoAutorizadoException e){
            return ResponseEntity.status(403).build();
        }
        
    }

}
