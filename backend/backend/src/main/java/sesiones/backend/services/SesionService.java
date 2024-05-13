package sesiones.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sesiones.backend.entities.Sesion;
import sesiones.backend.exceptions.SesionInexistenteException;
import sesiones.backend.exceptions.SesionNoAsociadaException;
import sesiones.backend.repositories.SesionRepository;

@Service
public class SesionService {
    private SesionRepository repoSesion;

    @Autowired
    public SesionService(SesionRepository repo) {
        this.repoSesion=repo;
    }

    public List<Sesion> getTodasSesiones(){
        return repoSesion.findAll();
    }

    public Sesion aniadirSesion(Sesion sesion) {
        if (sesion.getIdPlan() == null){
            throw new SesionNoAsociadaException();
        }
        return this.repoSesion.save(sesion);
    }

    public Optional<Sesion> obtenerSesion(Long id) {
		Optional<Sesion> s = repoSesion.findById(id);
		if (s.isPresent()){
			return s;
		} else {
			throw new SesionNoAsociadaException();
		}
	}

    public Sesion editarSesion(Sesion sesion) {
        if(!repoSesion.existsById(sesion.getId())){
            throw new SesionInexistenteException();
        }else{
            return this.repoSesion.save(sesion);
        }
    }

    public void deleteSesion(Long id){
        var sesion = repoSesion.findById(id);

        if(sesion.isPresent()){
            repoSesion.deleteById(id);
        }else{
            throw new SesionInexistenteException();
        }
    }

}
