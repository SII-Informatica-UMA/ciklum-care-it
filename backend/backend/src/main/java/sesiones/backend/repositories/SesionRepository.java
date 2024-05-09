package sesiones.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sesiones.backend.entities.Sesion;

public interface SesionRepository extends JpaRepository<Sesion, Long> {

	List<Sesion> findByInicio(String inicio);
	List<Sesion> findByIdPlan(Long idPlan);
}