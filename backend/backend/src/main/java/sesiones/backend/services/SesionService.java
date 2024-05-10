package sesiones.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import sesiones.backend.entities.Sesion;
import sesiones.backend.exceptions.SesionNoAsociadaException;
import sesiones.backend.repositories.SesionRepository;

public class SesionService {
    private SesionRepository repo;

    @Autowired
    public SesionService(SesionRepository repo) {
        this.repo=repo;
    }

    public List<Sesion> getTodasSesiones(){
        return repo.findAll();
    }

    public Sesion aniadirSesion(Sesion sesion) {
        if (sesion.getIdPlan() == null){
            throw new SesionNoAsociadaException();
        }
        return this.repo.save(sesion);
    }

    public Optional<Sesion> obtenerSesion(Long id) {
		Optional<Sesion> s = repo.findById(id);
		if (s.isPresent()){
			return s;
		} else {
			throw new SesionNoAsociadaException();
		}
	}
}
