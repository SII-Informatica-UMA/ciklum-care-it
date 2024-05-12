package sesiones.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sesiones.backend.entities.Sesion;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {

	List<Sesion> findByInicio(String inicio);
	List<Sesion> findByIdPlan(Long idPlan);
}