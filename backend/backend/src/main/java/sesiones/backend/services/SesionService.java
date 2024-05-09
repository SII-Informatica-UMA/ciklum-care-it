package sesiones.backend.services;

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
    public Sesion aniadirSesion(Sesion sesion) {
        if (sesion.getIdPlan() == null){
            throw new SesionNoAsociadaException();
        }
        return this.repo.save(sesion);
    }
}
